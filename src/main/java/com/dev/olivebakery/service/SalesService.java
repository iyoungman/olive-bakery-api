package com.dev.olivebakery.service;

import com.dev.olivebakery.domain.dto.SalesDto;
import com.dev.olivebakery.repository.SalesRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalesService {

    private final SalesRepository salesRepository;

    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public List<SalesDto.GetAverage> getAverageAnnualSales(){
        return salesRepository.getAverageSales(DayType.YEAR.name(), LocalDate.now());
    }

    public List<SalesDto.GetAverage> getAverageMonthlySales(int year) {
        return salesRepository.getAverageSales(DayType.MONTH.name(), LocalDate.of(year, 1, 1));
    }

    public List<SalesDto.GetAverage> getDailySales(int year, int month) {
        return salesRepository.getAverageSales(DayType.DAY.name(), LocalDate.of(year, month, 1));
    }

    public List<SalesDto.GetDashBoardData> getDashData(int year, int month) {
        return salesRepository.getDashData();
    }

    @Getter
    private enum DayType{
        YEAR, MONTH, DAY
    }
}
