package com.dev.olivebakery.domain.dto;


import com.dev.olivebakery.domain.enums.BreadState;
import com.dev.olivebakery.domain.enums.DayType;
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
        private boolean isSoldOut;
        private BreadState breadState;
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
        private boolean isSoldOut;
        private BreadState breadState;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class BreadSave{
        private String name;
        private int price;
//        private MultipartFile breadImage;
        private String description;
        private String detailDescription;
        private List<BreadIngredient> ingredientsList = new ArrayList<>();
        private List<DayType> dayTypes = new ArrayList<>();
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class BreadUpdateName {
        private String oldName;
        private String newName;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class BreadUpdate {
        private String oldName;
        private int price;
        private String description;
        private String detailDescription;
        private List<BreadIngredient> ingredientsList = new ArrayList<>();
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class BreadUpdateImage {
        private String oldName;
        private MultipartFile breadImage;
    }

}
