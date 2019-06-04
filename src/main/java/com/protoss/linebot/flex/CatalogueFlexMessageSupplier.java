package com.protoss.linebot.flex;

import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.*;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.unit.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

public class CatalogueFlexMessageSupplier implements Supplier<FlexMessage> {
    @Override
    public FlexMessage get() {
        final Image heroBlock = createHeroBlock();
        final Box bodyBlock = createBodyBlock();
        final Box footerBlock = createFooterBox();
        final Bubble bubble = Bubble.builder()
                .hero(heroBlock)
                .body(bodyBlock)
                .footer(footerBlock)
                .build();
        final Bubble bubble1 = Bubble.builder()
                .hero(heroBlock)
                .body(bodyBlock)
                .footer(footerBlock)
                .build();
        final Bubble bubble2 = Bubble.builder()
                .hero(heroBlock)
                .body(bodyBlock)
                .footer(footerBlock)
                .build();

//        final Bubble next = createSeeMoreBubble();

        final Carousel carousel = Carousel.builder()
                .contents(asList(bubble1, bubble1, bubble2, bubble1
//                        ,next
//                        ,bubble11
//                        , bubble12
//                        , bubble13,
//                        bubble14,bubble15,bubble16,bubble17,bubble18,bubble19,bubble20
                ))
                .build();
        return new FlexMessage("Restaurant Menu", carousel);
    }

    private Image createHeroBlock() {
        return Image.builder()
                .url("https://raw.githubusercontent.com/iphayao/line-bot-spring-boot-flex/master/src/main/resources/img/berger.png")
                .size(Image.ImageSize.FULL_WIDTH)
                .aspectRatio(Image.ImageAspectRatio.R20TO13)
                .aspectMode(Image.ImageAspectMode.Cover)
                .action(new URIAction("label", "https://example.com", new URIAction.AltUri(URI.create("https://example.com"))))
                .build();
    }

    private Box createBodyBlock() {
        final Text title = Text.builder()
                .text("    เรียก พนักงาน")
                .weight(Text.TextWeight.BOLD)
                .size(FlexFontSize.XXXL)
                .build();
//        final Box menus = createMenusBox();
//        final Box recipe = createRecipeBox();

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .spacing(FlexMarginSize.MD)
                .contents(asList(title))
                .build();
    }

    private Box createRecipeBox() {
        final Box recipe = Box.builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.XS)
                .contents(asList(
                        Text.builder()
                                .text("Source, Onions, Pickles, Lettuce & Cheese")
                                .size(FlexFontSize.XS)
                                .color("#aaaaaa")
                                .flex(1)
                                .build())).build();
        return recipe;
    }

    private Box createMenusBox() {
        final Box menu1 = Box.builder()
                .layout(FlexLayout.BASELINE)
                .contents(asList(
                        Icon.builder()
                                .url("https://raw.githubusercontent.com/iphayao/line-bot-spring-boot-flex/master/src/main/resources/img/regular.png").build(),
                        Text.builder().text("$10.5")
                                .weight(Text.TextWeight.BOLD)
                                .margin(FlexMarginSize.SM)
                                .flex(0)
                                .build(),
                        Text.builder().text("400kcl")
                                .size(FlexFontSize.SM)
                                .align(FlexAlign.END)
                                .color("#aaaaaa")
                                .build()

                ))
                .build();
        final Box menu2 = Box.builder()
                .layout(FlexLayout.BASELINE)
                .contents(asList(
                        Icon.builder()
                                .url("https://raw.githubusercontent.com/iphayao/line-bot-spring-boot-flex/master/src/main/resources/img/large.png").build(),
                        Text.builder().text("$15.5")
                                .weight(Text.TextWeight.BOLD)
                                .margin(FlexMarginSize.SM)
                                .flex(0)
                                .build(),
                        Text.builder().text("550kcl")
                                .size(FlexFontSize.SM)
                                .align(FlexAlign.END)
                                .color("#aaaaaa")
                                .build()

                ))
                .build();
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .spacing(FlexMarginSize.SM)
                .contents(asList(menu1, menu2))
                .build();
    }

    private Box createFooterBox() {
//        final Bu

        final Spacer spacer = Spacer
                .builder()
                .size(FlexMarginSize.XXL)
                .build();
        final Button button = Button.builder()
                .style(Button.ButtonStyle.PRIMARY)
                .color("#905c44")
                .action(new URIAction("Click", "https://example.com",
                        new URIAction.AltUri(URI.create("https://example.com"))))
//                .action(new URIAction("Add to Cart", "http://example.com"))
                .build();
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(asList(spacer, button))
                .build();
    }

    private Bubble createSeeMoreBubble() {
        return Bubble.builder()
                .body(Box.builder()
                                .layout(FlexLayout.VERTICAL)
                                .spacing(FlexMarginSize.SM)
                                .contents(asList(
                                        Button.builder()
//                                        .flex(1)
                                                .gravity(FlexGravity.CENTER)
                                                .action(new URIAction("Next", "line://app/1642485746-pNaOa3VJ",
                                                        new URIAction.AltUri(URI.create("line://app/1642485746-pNaOa3VJ"))))
//                                        .action(new URIAction("See more", "line://app/1642485746-pNaOa3VJ"))
                                                .build()
                                )).build()
                )
                .build();
    }

//    ----------------------------------------------------------------------------------
//    public FlexMessage get() {
//        final Bubble bubble1 = createBubble("Arm Chair, White",
//                "https://raw.githubusercontent.com/iphayao/line-bot-spring-boot-flex/master/src/main/resources/img/bubble1.png",
//                false);
//        final Bubble bubble2 = createBubble("Metal Desk Lamp",
//                "https://raw.githubusercontent.com/iphayao/line-bot-spring-boot-flex/master/src/main/resources/img/bubble2.png",
//                true);
//        final Bubble seeMore = createSeeMoreBubble();
//        final Carousel carousel = Carousel.builder()
//                .contents(asList(bubble1, bubble2, seeMore))
//                .build();
//        return new FlexMessage("Catalogue", carousel);
//    }
//
//    private Bubble createBubble(String title, String imageURL, Boolean isOutOfStock) {
//        final Image heroBlock = createHeroBlock(imageURL);
//        final Box bodyBlock = createBodyBlock(title, isOutOfStock);
//        final Box footerBlock = createFooterBlock(isOutOfStock);
//        return Bubble.builder()
//                .hero(heroBlock)
//                .body(bodyBlock)
//                .footer(footerBlock)
//                .build();
//    }
//
//    private Bubble createSeeMoreBubble() {
//        return Bubble.builder()
//                .body(Box.builder()
//                        .layout(FlexLayout.VERTICAL)
//                        .spacing(FlexMarginSize.SM)
//                        .contents(asList(
//                                Button.builder()
//                                        .flex(1)
//                                        .gravity(FlexGravity.CENTER)
//                                        .action(new URIAction("See more", "line://app/1642485746-pNaOa3VJ",
//                                                new URIAction.AltUri(URI.create("line://app/1642485746-pNaOa3VJ"))))
////                                        .action(new URIAction("See more", "line://app/1642485746-pNaOa3VJ"))
//                                        .build()
//                        )).build()
//                )
//                .build();
//    }
//
//    private Image createHeroBlock(String imageURL) {
//        return Image.builder()
//                .size(Image.ImageSize.FULL_WIDTH)
//                .aspectRatio(Image.ImageAspectRatio.R20TO13)
//                .aspectMode(Image.ImageAspectMode.Cover)
//                .url(imageURL)
//                .build();
//    }
//
//    private Box createBodyBlock(String title, Boolean isOutOfStock) {
//        final Text titleBlock = Text.builder()
//                .text(title)
//                .wrap(true)
//                .weight(Text.TextWeight.BOLD)
//                .size(FlexFontSize.XL).build();
//        final Box priceBlock = Box.builder()
//                .layout(FlexLayout.BASELINE)
////                .contents(asList(
////                        Text.builder().text("$" + price.split("\\.")[0])
////                                .wrap(true)
////                                .weight(Text.TextWeight.BOLD)
////                                .size(FlexFontSize.XL)
////                                .flex(0)
////                                .build(),
////                        Text.builder().text("." + price.split("\\.")[1])
////                                .wrap(true)
////                                .weight(Text.TextWeight.BOLD)
////                                .size(FlexFontSize.SM)
////                                .flex(0)
////                                .build()
////                ))
//                .build();
//        final Text outOfStock = Text.builder()
//                .text("Temporarily out of stock")
//                .wrap(true)
//                .size(FlexFontSize.XXS)
//                .margin(FlexMarginSize.MD)
//                .color("#FF5551")
//                .build();
//
//        FlexComponent[] flexComponents = {titleBlock, priceBlock};
//        List<FlexComponent> listComponent = new ArrayList<>(Arrays.asList(flexComponents));
//        if(isOutOfStock) {
//            listComponent.add(outOfStock);
//        }
//
//        return Box.builder()
//                .layout(FlexLayout.VERTICAL)
//                .spacing(FlexMarginSize.SM)
//                .contents(listComponent)
//                .build();
//    }
//
//    private Box createFooterBlock(Boolean isOutOfStock) {
//        final Button addToCartEnableButton = Button.builder()
//                .style(Button.ButtonStyle.PRIMARY)
//                .action(new URIAction("See more", "line://app/1642485746-pNaOa3VJ",
//                        new URIAction.AltUri(URI.create("line://app/1642485746-pNaOa3VJ"))))
//
////                .action(new URIAction("Add to Cart", "line://app/1632583510-Wl5wmB6D"))
//                .build();
//        final Button addToCartDisableButton = Button.builder()
//                .style(Button.ButtonStyle.PRIMARY)
//                .color("#aaaaaa")
//                .action(new URIAction("Add to Cart", "line://app/1642485746-pNaOa3VJ",
//                        new URIAction.AltUri(URI.create("line://app/1642485746-pNaOa3VJ"))))
//
////                .action(new URIAction("Add to Cart", "line://app/1632583510-Wl5wmB6D"))
//                .build();
//        final Button addToWishlistButton = Button.builder()
//                .action(new URIAction("Add to wishlist", "line://app/1642485746-pNaOa3VJ",
//                        new URIAction.AltUri(URI.create("line://app/1642485746-pNaOa3VJ"))))
//
////                .action(new URIAction("Add to wishlist", "line://app/1632583510-Wl5wmB6D"))
//                .build();
//        return Box.builder()
//                .layout(FlexLayout.VERTICAL)
//                .spacing(FlexMarginSize.SM)
//                .contents(asList((!isOutOfStock) ? addToCartEnableButton : addToCartDisableButton, addToWishlistButton))
//                .build();
//    }
}
