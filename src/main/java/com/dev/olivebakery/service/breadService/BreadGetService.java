package com.dev.olivebakery.service.breadService;

import com.dev.olivebakery.domain.dto.BreadDto;
import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.domain.entity.Ingredients;
import com.dev.olivebakery.domain.enums.DayType;
import com.dev.olivebakery.exception.UserDefineException;
import com.dev.olivebakery.repository.BreadRepository;
import com.dev.olivebakery.repository.DaysRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BreadGetService {

    private BreadRepository breadRepository;
    private DaysRepository daysRepository;

    public BreadGetService(BreadRepository breadRepository, DaysRepository daysRepository){
        this.breadRepository = breadRepository;
        this.daysRepository = daysRepository;
    }

    // 요일 별 빵 가져오기
    public List<BreadDto.BreadGetAll> getBreadByDay(DayType day){
        List<Bread> breads = daysRepository.findByDayType(day);
        return breads2BreadGetAll(breads);
    }

    private List<BreadDto.BreadGetAll> breads2BreadGetAll(List<Bread> breads){
        List<BreadDto.BreadGetAll> breadGetAlls = new ArrayList<>();
        breads.forEach(bread -> {
            breadGetAlls.add(
                    BreadDto.BreadGetAll.builder()
                            .name(bread.getName())
                            .price(bread.getPrice())
                            .description(bread.getDescription())
                            .isSoldOut(bread.getIsSoldOut())
                            .breadState(bread.getState())
                            .build());
        });

        return breadGetAlls;
    }

    public BreadDto.BreadGetDetail getBreadDetails(String name){
        Bread bread = breadRepository.findByName(name)
                .orElseThrow(() -> new UserDefineException(name + "이란 빵은 존재하지 않습니다."));

        List<BreadDto.BreadIngredient> breadIngredientList = ingredientList2Dto(bread.getIngredients());

        return BreadDto.BreadGetDetail.builder()
                .name(bread.getName())
                .price(bread.getPrice())
                .detailDescription(bread.getDetailDescription())
                .ingredientsList(breadIngredientList)
                .isSoldOut(bread.getIsSoldOut())
                .breadState(bread.getState())
                .build();
    }

    private List<BreadDto.BreadIngredient> ingredientList2Dto(List<Ingredients> ingredientsList) {

        List<BreadDto.BreadIngredient> ingredientDtoList = new ArrayList<>();

        ingredientsList.forEach(ingredient -> ingredientDtoList.add(
                BreadDto.BreadIngredient.builder()
                        .name(ingredient.getName())
                        .origin(ingredient.getOrigin())
                        .build()
        ));

        return ingredientDtoList;
    }


}
