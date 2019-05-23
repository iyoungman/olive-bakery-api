package com.dev.olivebakery.controller;

import com.dev.olivebakery.domain.dto.SignDto;
import com.dev.olivebakery.service.SignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SignControllerTest {

    @Autowired
    private MockMvc mvc;


    private ObjectMapper om = new ObjectMapper();

    @MockBean
    private SignService signService;

    @Test
    public void signUpAdmin() {
    }

    @Test
    public void signUpClient() throws Exception{
        SignDto.SignUp signUp = SignDto.SignUp.builder()
                .email("cnsth1009@naver.com")
                .pw("1234")
                .name("박춘소")
                .phoneNumber("010-0000-0000")
                .build();

        mvc.perform(post("/olive/sign/client")
                        .content(om.writeValueAsString(signUp))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void signIn() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getMember() {
    }

    @Test
    public void getWholeMembers() {
    }
}