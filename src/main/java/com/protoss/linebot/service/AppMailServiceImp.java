package com.protoss.linebot.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.protoss.linebot.entity.LineData;
import com.protoss.linebot.entity.MasterDataDetail;
import com.protoss.linebot.repository.LineDataRepository;
import com.protoss.linebot.repository.MasterDataDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AppMailServiceImp implements AppMailDataService {

    @Autowired
    private MasterDataDetailRepository masterDataDetailRepository;

    @Autowired
    private LineDataRepository lineDataRepository;

    public static List<String> LIST_KEYWORD = new ArrayList<>();
    public static List<String> LIST_PROGRAM = new ArrayList<>();

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<LineData> getUserId() {
        return lineDataRepository.findAll();
    }


    @Override
    public void SaveByJsonCus(String json) {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss");//ตั้ง gson format เพื่อ save ข้อมูลลง database

        LOGGER.info("Json : {}",json);
        LineData lineData = gsonBuilder.create().fromJson(json, LineData.class);

        lineDataRepository.save(lineData);
        LOGGER.info("Save By Json : {}",lineData.getId());
    }

//    @Override
//    public ResponseEntity<String> appby(String tableId) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8083/RCD".concat("/Dashboard/savebyTable");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam(tableId);
//        HttpEntity<String> entity = new HttpEntity<String>("", headers);
//        LOGGER.info("request :{}", url);
//        try {
//            return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
//        } catch (Exception e) {
//            LOGGER.error("{}", e.getMessage());
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void SaveByTableId(String tableId) {
        LineData lineData = new LineData();
        lineData.setTableId(tableId);
        lineDataRepository.save(lineData);
    }

    @Override
    public List<MasterDataDetail> masterDatakey(Long id, String code) {
        List<MasterDataDetail> listKeyword = masterDataDetailRepository.findMasterDataDetailsByIdEquals(id, code);
        LOGGER.info("listKeyword : {}", listKeyword.size());
        String resultKeyword = "";
        List<String> keywordSplitList = null;
        resultKeyword = listKeyword.get(0).getVariable1();
        LOGGER.info("num : {}", resultKeyword);
        switch (code) {
            case "program.list": {
                LIST_PROGRAM = null;
                LIST_PROGRAM = new ArrayList<>();
                keywordSplitList = Arrays.asList(resultKeyword.split("\\s*,\\s*"));
                LOGGER.info("program.list : {}", keywordSplitList);
                for (String progran : keywordSplitList) {
                    LIST_PROGRAM.add(progran);
                }
                LOGGER.info("program : {}", LIST_PROGRAM);
            }

            break;
            case "keyword.list": {
                LIST_KEYWORD = null;
                LIST_KEYWORD = new ArrayList<>();
                keywordSplitList = Arrays.asList(resultKeyword.split("\\s*,\\s*"));
                LOGGER.info("Keywor.list : {}", keywordSplitList);
                for (String keyword : keywordSplitList) {
                    LIST_KEYWORD.add(keyword);
                }
                LOGGER.info("Keywor : {}", LIST_KEYWORD);
            }
            break;
            default:
                break;
        }
        LOGGER.info("Keywor : {}", LIST_KEYWORD);
        LOGGER.info("program : {}", LIST_PROGRAM);
        return null;
    }

    public static boolean checkTextMatches(String str) {
        int countKeyword = 0;
        int countProgram = 0;

        for (String keyword : LIST_KEYWORD) {
            if (str.indexOf(keyword) >= 0) {
                countKeyword++;
            }
        }
        for (String keyword : LIST_PROGRAM) {
            if (str.indexOf(keyword) >= 0) {
                countProgram++;
                break;
            }
        }
        if (countKeyword != 0 && countProgram != 0) {
            return true;

        } else {
            return false;
        }
    }



}
