package com.dev.olivebakery.controller;

import com.dev.olivebakery.domain.dto.SalesDto;
import com.dev.olivebakery.service.SalesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/olive/sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/graph")
    public List<SalesDto.GetAverage> getAverageAnnualSales(){
        return salesService.getAverageAnnualSales();
    }

    @GetMapping("/graph/year/{year}")
    public List<SalesDto.GetAverage> getAverageMonthlySales(@PathVariable("year") int year){
        return salesService.getAverageMonthlySales(year);
    }

    @GetMapping("/graph/year/{year}/month/{month}")
    public List<SalesDto.GetAverage> getDailySales(@PathVariable("year") int year, @PathVariable("month") int month){
        return salesService.getDailySales(year, month);
    }

    @GetMapping("/dash/year/{year}/month/{month}")
    public List<SalesDto.GetDashBoardData> getDashData(@PathVariable("year") int year, @PathVariable("month") int month){
        return salesService.getDashData(year, month);
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
