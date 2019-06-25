package com.protoss.linebot;

import com.google.common.io.ByteStreams;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingClientImpl;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.*;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.*;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.protoss.linebot.flex.*;
import com.protoss.linebot.helper.RichMenuHelper;
import com.protoss.linebot.repository.LineDataRepository;
import com.protoss.linebot.service.AppMailServiceImp;
import com.protoss.linebot.utils.ReportParams;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

//import java.io.*;

//import com.linecorp.bot.client.LineMessagingClient;
//import com.linecorp.bot.line.model.event.Event;
//import com.linecorp.bot.model.event.MemberJoinedEvent;
//import com.linecorp.bot.model.event.*;


@Slf4j
@LineMessageHandler
public class LineBotController {


    @Autowired
    private LineMessagingClient lineMessagingClient;

    @Autowired
    private LineMessagingClientImpl lineMessagingClientImpl;

    @Autowired
    ApplicationContext context;

    @Autowired
    LineDataRepository lineDataRepository;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    static int i;

    /////////////////////////////รับข้อความ////////////////////////////////
    @EventMapping
    public void handleTextMessage(MessageEvent<TextMessageContent> event) throws IOException, Exception, JRException {
        log.info(event.toString());
        handleTextContent(event.getReplyToken(), event, event.getMessage());
    }

    /////////////////////////////Sticker////////////////////////////////
    @EventMapping
    public void handleStickerMessage(MessageEvent<StickerMessageContent> event) {
        log.info(event.toString());
        StickerMessageContent message = event.getMessage();

        String stickerPackageId = "11537";
        String stickerStickerId = "52002768";

        log.info("StickerPackageId : {}", message.getPackageId());
        log.info("StickerStickerId : {}", message.getStickerId());

        reply(event.getReplyToken(), new StickerMessage(
                stickerPackageId, stickerStickerId

        ));
    }

    /////////////////////////////Location////////////////////////////////
    @EventMapping
    public void handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        log.info(event.toString());
        LocationMessageContent message = event.getMessage();
        log.info("Title : {}", message.getTitle());
        log.info("Address : {}", message.getAddress());
        log.info("Latitude : {}", message.getLatitude());
        log.info("Longitude : {}", message.getLongitude());

        reply(event.getReplyToken(), new LocationMessage(
                (message.getTitle() == null) ? "Location replied" : message.getTitle(),
                message.getAddress(),
                message.getLatitude(),
                message.getLongitude()
        ));
    }

    /////////////////////////////รูปภาพ////////////////////////////////
    @EventMapping
    public void handleImageMessage(MessageEvent<ImageMessageContent> event) {
        log.info(event.toString());
        ImageMessageContent content = event.getMessage();
        String replyToken = event.getReplyToken();

        try {
            MessageContentResponse response = lineMessagingClient.getMessageContent(content.getId()).get();
            DownloadedContent jpg = saveContent("jpg", response);

            this.reply(replyToken, Arrays.asList(
                    new ImageMessage(jpg.getUri(), jpg.getUri()),
                    new TextMessage("มี keyword ไม่มี @"),
                    new TextMessage(jpg.getUri()),
                    new TextMessage(jpg.getUri())
            ));

        } catch (InterruptedException | ExecutionException e) {
            reply(replyToken, new TextMessage("Cannot get image: " + content));
            throw new RuntimeException(e);
        }

    }

    /////////////////////////////LINE Beacon///////////////////////////
    @EventMapping
    public void handleBeaconEvent(BeaconEvent event) throws IOException {

        String replyToken = event.getReplyToken();
        String userId = event.getSource().getUserId();

        String pathImageFlex = new ClassPathResource("richmenu/Home (1).jpg").getFile().getAbsolutePath();
        String pathConfigFlex = new ClassPathResource("richmenu/home.yml").getFile().getAbsolutePath();
        RichMenuHelper.createRichMenu(lineMessagingClient, pathConfigFlex, pathImageFlex, userId);
        this.reply(replyToken, new NewsFlexMessageSupplier().get());
    }

    @EventMapping
    public void handlememberJoined(MemberJoinedEvent event) throws IOException {
//        String userId = event.getSource().getUserId();
//
//        String pathImageFlex = new ClassPathResource("richmenu/Home (1).jpg").getFile().getAbsolutePath();
//        String pathConfigFlex = new ClassPathResource("richmenu/home.yml").getFile().getAbsolutePath();
//        RichMenuHelper.createRichMenu(lineMessagingClient, pathConfigFlex, pathImageFlex, userId);
    }

    @EventMapping
    public void handleJoined(JoinEvent event) throws IOException {
//        String e = event.getSource().getUserId();
//        String replyToken = event.getReplyToken();
//        String userId = event.getSource().getUserId();
//
//        String pathImageFlex = new ClassPathResource("richmenu/Home (1).jpg").getFile().getAbsolutePath();
//        String pathConfigFlex = new ClassPathResource("richmenu/home.yml").getFile().getAbsolutePath();
//        RichMenuHelper.createRichMenu(lineMessagingClient, pathConfigFlex, pathImageFlex, userId);
    }


    private void handleTextContent(String replyToken, Event event, TextMessageContent content) throws Exception, IOException, JRException {
        String text = content.getText();
        String userId = event.getSource().getUserId();
        LOGGER.info("dssd", event.getSource().toString());
        LocalTime time = LocalTime.now();
        int listHour = time.getHour();
        int listMinute = time.getMinute();

        if(listHour >= 6 && (listHour <= 17 && listMinute <= 60)){
//        if(listHour >= 6 && (listHour <= 13 && listMinute <= 60)){
                switch (text) {
                    case "Flex": {
                        String pathImageFlex = new ClassPathResource("richmenu/Newmenu_optimized.jpg").getFile().getAbsolutePath();
                        String pathConfigFlex = new ClassPathResource("richmenu/richmenu-flexs.yml").getFile().getAbsolutePath();
                        RichMenuHelper.createRichMenu(lineMessagingClient, pathConfigFlex, pathImageFlex, userId);
//                lineMessagingClient.getProfile(userId)
//                        .whenComplete((profile, throwable) -> {
//                            LOGGER.info("Flex{}",usid);
//                            usid = profile.getUserId();
////                            lineDataRepository.save(new LineData(profile.getUserId()));
//                        });
                        break;
                    }
                    case "เก็บตัง": {
                        RichMenuHelper.deleteRichMenu(lineMessagingClient, userId);
                        break;
                    }
                    case "เรียกพนักงาน": {
                        this.reply(replyToken, new RestaurantFlexMessageSupplier().get());
                        break;
                    }
                    case "หน้าหลัก": {
                        this.reply(replyToken, new RestaurantMenuFlexMessageSupplier().get());
                        break;
                    }
                    case "ดูคิว": {
                        LOGGER.info("UserId message{}", userId);
                        generateReport(userId);
                        getJpgImge();
                        Path tempFile = Application.downloadedContentDir.resolve("imgpdf" + i + "-0.jpg");
                        DownloadedContent jpg = new DownloadedContent(tempFile, createUri("/downloaded/" + tempFile.getFileName()));
                        this.reply(replyToken, Arrays.asList(
                                new ImageMessage(jpg.getUri(), jpg.getUri())
                        ));
                        break;
                    }
                    case "Ticket": {
                        this.reply(replyToken, new TicketFlexMessageSupplier().get());
                        break;
                    }
                    case "เรียกเมนู": {
                        this.reply(replyToken, new CatalogueFlexMessageSupplier().get());
                        break;
                    }
                    default:
                        String pathImageFlex = new ClassPathResource("richmenu/home.yml").getFile().getAbsolutePath();
                        String pathConfigFlex = new ClassPathResource("richmenu/richmenu-flexs.yml").getFile().getAbsolutePath();
                        RichMenuHelper.createRichMenu(lineMessagingClient, pathConfigFlex, pathImageFlex, userId);
                        boolean hasText = text.contains("@");
                        boolean hasText3 = text.contains("ขอเข้ากลุ่ม");
                        if (hasText3 == true) {
                            reply(replyToken, Arrays.asList(
                                    new TextMessage("https://line.me/R/ti/g/vYYHUCuMG_")
                            ));
                        } else if ((AppMailServiceImp.checkTextMatches(text) == true) && (hasText == false)) {
                            if (userId != null) {
                                lineMessagingClient.getProfile(userId)
                                        .whenComplete((profile, throwable) -> {
                                            if (throwable != null) {
                                                this.replyText(replyToken, throwable.getMessage());
                                                return;
                                            }
                                            this.reply(replyToken, Arrays.asList(
                                                    new TextMessage("มี keyword ไม่มี @"),
                                                    new TextMessage("ชื่อคุณคือ: " + profile.getDisplayName()),
                                                    new TextMessage("ID คุณคือ: " + profile.getUserId())
                                            ));
                                        });
                            }
                        } else if ((hasText == true) && (AppMailServiceImp.checkTextMatches(text) == true)) {
                            if (userId != null) {
                                reply(replyToken, Arrays.asList(
                                        new TextMessage("มี keyword และมี @"),
                                        new TextMessage(text)
                                ));
                            }
                        }
                }
        }else{
            RichMenuHelper.deleteRichMenu(lineMessagingClient, userId);
            reply(replyToken, Arrays.asList(new TextMessage("ไม่ได้อยู่ในช่วงเวลา")));
        }

    }

    private static DownloadedContent saveContent(String ext, MessageContentResponse response) {
        log.info("Content-type: {}", response);
        DownloadedContent tempFile = createTempFile(ext);
        try (OutputStream outputStream = Files.newOutputStream(tempFile.path)) {
            ByteStreams.copy(response.getStream(), outputStream);
            log.info("Save {}: {}", ext, tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static DownloadedContent createTempFile(String ext) {
//        String fileName = LocalDateTime.now() + "-" + UUID.randomUUID().toString() + "." + ext;

        log.info("pdf{}", i);
        Path tempFile = Application.downloadedContentDir.resolve("imgpdf" + i + "-0.jpg");
        tempFile.toFile().deleteOnExit();
//        return new DownloadedContent(tempFile, createUri("/downloaded/" + tempFile.getFileName()));
        return new DownloadedContent(tempFile, createUri("/downloaded/" + tempFile.getFileName()));

    }

    private static String createUri(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path).toUriString();
    }

    @Value
    public static class DownloadedContent {
        Path path;
        String uri;
    }

    private void replyText(@NonNull String replyToken, @NonNull String message) {
        if (replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken is not empty");
        }

        if (message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "...";
        }
        this.reply(replyToken, new TextMessage(message));
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse response = lineMessagingClient.replyMessage(
                    new ReplyMessage(replyToken, messages)
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateReport(String userIdd) throws JRException, IOException {
        JasperReport jasperReport;
//        JasperPrint jasperPrint;
        Resource resource = context.getResource("classpath:reports/car_list.jrxml");

        InputStream inputStream = resource.getInputStream();
        JasperReport report = JasperCompileManager.compileReport(inputStream);

        Map<String, Object> reportParameters = new HashMap<>();

        reportParameters.put(ReportParams.UI, userIdd);

        LOGGER.info("Add UserId:{}", userIdd);

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, reportParameters, new JREmptyDataSource());

        i++;
        JasperExportManager.exportReportToPdfStream(jasperPrint,
                new FileOutputStream("src/main/resources/reports/car_list" + i + ".pdf")
        );
    }

    public void getJpgImge() throws Exception {
        PDDocument document = PDDocument.load(new File("src/main/resources/reports/car_list" + i + ".pdf"));
        LOGGER.info("pdf: {}", document);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            ImageIOUtil.writeImage(bim, String.format("src/main/resources/reports/imgpdf" + i) + "-" + page++ + "." + "jpg", 300);
        }
        document.close();

    }
}
