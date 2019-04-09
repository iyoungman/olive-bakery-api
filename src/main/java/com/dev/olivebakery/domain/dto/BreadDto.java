package com.dev.olivebakery.domain.dto;


import com.dev.olivebakery.domain.enums.BreadState;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BreadDto {

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class BreadIngredient{
        private String name;
        private String origin;

//        @Builder
//        public BreadIngredient(String ingredient, String origin){
//            this.ingredient = ingredient;
//            this.origin = origin;
//        }
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class BreadGetAll {
        private String name;
        private int price;
        private String picturePath;
        private String description;
        private boolean soldOut;
        private BreadState breadState;

//        @Builder
//        public BreadGetAll(String name, int price, String picturePath, String description, boolean soldOut, BreadState breadState){
//            this.name = name;
//            this.price = price;
//            this.picturePath = picturePath;
//            this.description = description;
//            this.soldOut = soldOut;
//            this.breadState = breadState;
//        }
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class BreadGetDetail{
        private String name;
        private int price;
        private String picturePath;
        private String detailDescription;
        private List<BreadIngredient> ingredientsList = new ArrayList<>();
        private boolean soldOut;
        private BreadState breadState;
//        @Builder
//        public BreadGetDetail(String name, int price, String picturePath, String detailDescription, boolean soldOut, List<BreadIngredient> ingredientsList, BreadState breadState){
//            this.name = name;
//            this.price = price;
//            this.picturePath = picturePath;
//            this.detailDescription = detailDescription;
//            this.soldOut = soldOut;
//            this.ingredientsList = ingredientsList;
//            this.breadState = breadState;
//        }
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class BreadSave{
        private String name;
        private int price;
        private MultipartFile breadImage;
        private String description;
        private String detailDescription;
        private List<BreadIngredient> ingredientsList = new ArrayList<>();
//        @Builder
//        public BreadSave(String name, int price, String picturePath, String description, String detailDescription, List<BreadIngredient> ingredientsList){
//            this.name = name;
//            this.price = price;
//            this.picturePath = picturePath;
//            this.description = description;
//            this.detailDescription = detailDescription;
//            this.ingredientsList = ingredientsList;
//        }
    }

}
