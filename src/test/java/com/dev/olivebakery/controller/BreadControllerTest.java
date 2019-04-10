package com.dev.olivebakery.controller;

import com.dev.olivebakery.domain.dto.BreadDto;
import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.repository.BreadRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BreadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BreadRepository breadRepository;

    BreadDto.BreadSave breadDto;
    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        List<BreadDto.BreadIngredient> breadIngredientList = new ArrayList<>();
        BreadDto.BreadIngredient ingredient = BreadDto.BreadIngredient.builder().name("밀가루").origin("한국").build();

        breadIngredientList.add(ingredient);

        breadDto = BreadDto.BreadSave.builder()
                .name("치아바타")
                .price(10000)
                .description("맛있는 빵")
                .detailDescription("자세한 맛있는 빵")
                .ingredientsList(breadIngredientList).build();

        Bread bread = Bread.builder().name("치아바타").build();
        breadRepository.save(bread);

    }

    @Test
    public void test1() throws Exception{
//        this.mockMvc.perform(MockMvcRequestBuilders
////                .post("/olive/bread/")
////                .content(asJson))
//
//        this.mockMvc.perform(MockMvcRequestBuilders
//            .put("olive/bread/name")
//            .content(mapper.writeValueAsString(breadDto))
//            .contentType(MediaType.APPLICATION_JSON.getType())
//            .andExpect(state().))
    }

}