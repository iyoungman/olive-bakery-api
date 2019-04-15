package com.dev.olivebakery.controller;

import com.dev.olivebakery.domain.dto.BreadDto;
import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.domain.enums.DayType;
import com.dev.olivebakery.exception.UserDefineException;
import com.dev.olivebakery.service.BreadService;
import com.dev.olivebakery.service.breadService.BreadSaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/olive/bread")
@Log4j2
public class BreadController {

    private final BreadSaveService breadSaveService;

    public BreadController(BreadSaveService breadSaveService){
        this.breadSaveService = breadSaveService;
    }

//    @ApiOperation("요일별 빵 정보 가져오기")
//    @GetMapping("/day/{day}")
//    public List<BreadDto.BreadGetAll> getBread(@PathVariable DayType day){
//        //return breadSaveService.getBreadByDay(day);
//    }

//    @ApiOperation("빵 상세정보 가져오기")
//    @GetMapping("/name/{name}")
//    public BreadDto.BreadGetDetail getDetail(@PathVariable String name){
//        //return breadService.getBreadDetails(name);
//    }

    @ApiOperation("빵 정보 수정")
    @PutMapping
    public void updateBread(@RequestBody BreadDto.BreadSave bread){

    }

    @ApiOperation("빵, 이미지 같이 저장")
    @PostMapping()
    public void saveBreadAndImage(@RequestPart MultipartFile files,
                                  @RequestParam String json) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        BreadDto.BreadSave breadSave = objectMapper.readValue(json, BreadDto.BreadSave.class);
        breadSaveService.saveBread(breadSave, files);
        //breadService.saveImage(files);
    }

    @ApiOperation("빵 이름 수정")
    @PutMapping(value = "/name")
    public void updateBreadName(@RequestBody BreadDto.BreadUpdateName bread){
//        String updateName = breadService.updateName(bread);
//
//        if(!updateName.equals(bread.getNewName())){
//            throw new UserDefineException("저장 실패");
//        }
    }

    @ApiOperation("빵 정보 수정")
    @PutMapping()
    public void updateBread(@RequestBody BreadDto.BreadUpdateName bread){

    }

    @ApiOperation("빵 사진 수정")
    @PutMapping()
    public void updateBreadIamge(@RequestBody BreadDto.BreadUpdateImage bread){

    }


    @ApiOperation("빵 삭제")
    @DeleteMapping("/name/{name}")
    public void deleteBread(@PathVariable String name){

    }
}