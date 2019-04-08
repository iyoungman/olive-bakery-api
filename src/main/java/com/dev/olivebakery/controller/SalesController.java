package com.dev.olivebakery.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/olive/sales")
public class SalesController {

    @GetMapping("/graph/year/{year}")
    public void getAverageAnnualSales(@PathVariable("year") int year){

    }

    @GetMapping("/graph/year/{year}/month/{month}")
    public void getAverageMonthlySales(@PathVariable("year") int year, @PathVariable("month") int month){

    }

    @GetMapping("graph/year/{year}/month/{month}/day/{day}")
    public void getDailySales(@PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day){

    }

    @GetMapping("/dash/year/{year}/month/{month}")
    public void getDashData(@PathVariable("year") int year, @PathVariable("month") int month){

    }

    @PostMapping
    public void saveSaleInfo(){

    }

    @PutMapping
    public void updateSaleInfo(){

    }
    @DeleteMapping
    public void deleteSaleInfo(){

    }
}
