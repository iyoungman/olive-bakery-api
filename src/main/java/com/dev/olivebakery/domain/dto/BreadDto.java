package com.dev.olivebakery.domain.dto;


import com.dev.olivebakery.domain.enums.BreadState;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BreadDto {

    @Getter
    @NoArgsConstructor
    public static class Ingredient{
        private String ingredient;
        private String origin;

        @Builder
        public Ingredient(String ingredient, String origin){
            this.ingredient = ingredient;
            this.origin = origin;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class GetAll {
        private String name;
        private int price;
        private String picturePath;
        private String description;
        private boolean soldOut;
        private BreadState breadState;

        @Builder
        public GetAll(String name, int price, String picturePath, String description, boolean soldOut, BreadState breadState){
            this.name = name;
            this.price = price;
            this.picturePath = picturePath;
            this.description = description;
            this.soldOut = soldOut;
            this.breadState = breadState;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class GetDetail{
        private String name;
        private int price;
        private String picturePath;
        private String detailDescription;
        private List<Ingredient> ingredientsList = new ArrayList<>();
        private boolean soldOut;
        private BreadState breadState;
        @Builder
        public GetDetail(String name, int price, String picturePath, String detailDescription, boolean soldOut, List<Ingredient> ingredientsList, BreadState breadState){
            this.name = name;
            this.price = price;
            this.picturePath = picturePath;
            this.detailDescription = detailDescription;
            this.soldOut = soldOut;
            this.ingredientsList = ingredientsList;
            this.breadState = breadState;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Save{
        private String name;
        private int price;
        private String picturePath;
        private String description;
        private String detailDescription;
        private List<Ingredient> ingredientsList = new ArrayList<>();
        @Builder
        public Save(String name, int price, String picturePath, String description, String detailDescription, List<Ingredient> ingredientsList){
            this.name = name;
            this.price = price;
            this.picturePath = picturePath;
            this.description = description;
            this.detailDescription = detailDescription;
            this.ingredientsList = ingredientsList;
        }
    }

}
