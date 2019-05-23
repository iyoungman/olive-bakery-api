package com.dev.olivebakery.controller;

import com.dev.olivebakery.domain.dto.BreadDto;
import com.dev.olivebakery.service.IngredientsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/olive/ingredients")
@RequiredArgsConstructor
public class IngredientsController {

    private final IngredientsService ingredientsService;

    @ApiOperation("빵 성분 추가")
    @PostMapping()
    public void saveIngredients(@RequestBody BreadDto.BreadIngredient breadIngredient){

        ingredientsService.saveIngredients(breadIngredient);
    }

    @ApiOperation("빵 성분 삭제")
    @DeleteMapping()
    public void deleteIngredients(@RequestBody BreadDto.BreadIngredient breadIngredient){
        ingredientsService.deleteIngredients(breadIngredient);
    }

    @ApiOperation("빵 성분 여러개 등록")
    @PostMapping("/list")
    public void saveIngredientsList(@RequestBody List<BreadDto.BreadIngredient> breadIngredientList){
        ingredientsService.saveIngredientsList(breadIngredientList);
    }

    @ApiOperation("빵 성분 리스틑 가져오기")
    @GetMapping()
    public List<BreadDto.BreadIngredient> getIngredientsList(){
        return ingredientsService.getIngredientsList();
    }
}
