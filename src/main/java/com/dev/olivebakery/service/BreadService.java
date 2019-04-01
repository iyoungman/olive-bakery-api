package com.dev.olivebakery.service;

import com.dev.olivebakery.domain.dto.BreadDto;
import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.domain.enums.DayType;
import com.dev.olivebakery.exception.UserDefineException;
import com.dev.olivebakery.repository.BreadRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by YoungMan on 2019-02-12.
 */

@Service
public class BreadService {

    private final BreadRepository breadRepository;

    public BreadService(BreadRepository breadRepository) {
        this.breadRepository = breadRepository;
    }

    public Bread findByName(String breadName) {
        return breadRepository.findByName(breadName)
                .orElseThrow(() -> new UserDefineException("해당 이름의 빵이 없습니다."));
    }

    public List<Bread> findsByNames(List<String> breadNames) {
        return breadRepository.findByNameIn(breadNames);
    }

    public int getFinalPrice(LinkedHashMap<String, Integer> breadInfos) {
        List<String> breadNames = new ArrayList<>(breadInfos.keySet());
        List<Integer> counts = new ArrayList<>(breadInfos.values());
        List<Bread> breads = findsByNames(breadNames);
        int finalPrice = 0;

        for (int i = 0; i < breadInfos.size(); i++) {
            finalPrice = finalPrice + (breads.get(i).getPrice() * counts.get(i));
        }

        return finalPrice;
    }

    public List<BreadDto.GetAll> getBreadByDay(DayType day){
        List<Bread> breadList = breadRepository.findByDays(day);
        List<BreadDto.GetAll> breadGetAll = new ArrayList<>();
        breadList.forEach(bread -> {
            boolean isSoldOut = false;
            if(bread.getSoldOut() != null)
                isSoldOut = bread.getSoldOut().getDate().isEqual(LocalDate.now());

            breadGetAll.add(
                BreadDto.GetAll.builder()
                        .picturePath(bread.getPicturePath())
                        .name(bread.getName())
                        .price(bread.getPrice())
                        .description(bread.getDescription())
                        .soldOut(isSoldOut)
                        .breadState(bread.getState())
                        .build());

        });

        return breadGetAll;
    }

    public BreadDto.GetDetail getBreadDetails(String name){
        Bread bread = breadRepository.findByName(name)
                .orElseThrow(() -> new UserDefineException(name + "이란 빵은 존재하지 않습니다."));
        boolean isSoldOut = false;
        if(bread.getSoldOut() != null)
            isSoldOut = bread.getSoldOut().getDate().isEqual(LocalDate.now());

        List<BreadDto.Ingredient> ingredientList = new ArrayList<>();
        bread.getIngredients().forEach(ingredient -> ingredientList.add(
                BreadDto.Ingredient.builder()
                        .ingredient(ingredient.getName())
                        .origin(ingredient.getOrigin())
                        .build()
        ));

        return BreadDto.GetDetail.builder()
                .name(bread.getName())
                .price(bread.getPrice())
                .picturePath(bread.getPicturePath())
                .detailDescription(bread.getDetailDescription())
                .ingredientsList(ingredientList)
                .soldOut(isSoldOut)
                .breadState(bread.getState())
                .build();
    }

    public void updateBread(BreadDto.Save updateBread){
        Bread bread = breadRepository.findByName(updateBread.getName())
                .orElseThrow(() -> new UserDefineException(updateBread.getName() + "이란 빵은 존재하지 않습니다."));

    }
}
