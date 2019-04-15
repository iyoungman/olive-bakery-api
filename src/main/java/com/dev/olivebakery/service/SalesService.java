package com.dev.olivebakery.service;

import com.dev.olivebakery.domain.dto.SalesDto;
import com.dev.olivebakery.domain.enums.SaleType;
import com.dev.olivebakery.repository.SalesRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {

    private final SalesRepository salesRepository;

    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public List<SalesDto.GetGraphData> getAverageAnnualSales(){
        List<SalesDto.GetGraphTmp> averageSales = salesRepository.getAverageSales(DayType.YEAR.name(), LocalDate.now());
        if(averageSales.isEmpty())
            return null;

        return convertToGraphData(averageSales);
    }

    public List<SalesDto.GetGraphData> getAverageMonthlySales(int year) {
        List<SalesDto.GetGraphTmp> averageSales = salesRepository.getAverageSales(DayType.MONTH.name(), LocalDate.of(year, 1, 1));
        if(averageSales.isEmpty())
            return null;

        return convertToGraphData(averageSales);
    }

    public List<SalesDto.GetGraphData> getDailySales(int year, int month) {
        List<SalesDto.GetGraphTmp> averageSales = salesRepository.getAverageSales(DayType.DAY.name(), LocalDate.of(year, month, 1));
        if(averageSales.isEmpty())
            return null;

        return convertToGraphData(averageSales);
    }

    public List<SalesDto.GetDashBoardData> getDashData(int year, int month) {
        List<SalesDto.GetDashBoardTmp> tblData = salesRepository.getDashData(LocalDate.of(year, month, 1));
        List<SalesDto.GetDashBoardData> dashBoardDataList = new ArrayList<>();
        LocalDate localDate = tblData.get(0).getDate();
        SalesDto.GetDashBoardData dashBoardData = new SalesDto.GetDashBoardData();

        for (SalesDto.GetDashBoardTmp tmpData : tblData) {
            if(!localDate.isEqual(tmpData.getDate())) {
                dashBoardData.setTotalSale(dashBoardData.getOfflineSale()+dashBoardData.getReservationSale());
                dashBoardData.setDate(localDate);
                dashBoardDataList.add(dashBoardData);
                dashBoardData = new SalesDto.GetDashBoardData();
                localDate = tmpData.getDate();
            }

            if(SaleType.RESERVATION.name().equals(tmpData.getSaleType().name())){
                dashBoardData.setReservationSale(tmpData.getSales());
                dashBoardData.setReservationCnt(tmpData.getReservationCnt());
            }else if(SaleType.OFFLINE.name().equals(tmpData.getSaleType().name())){
                dashBoardData.setOfflineSale(tmpData.getSales());
            }
        }
        dashBoardData.setTotalSale(dashBoardData.getOfflineSale()+dashBoardData.getReservationSale());
        dashBoardData.setDate(localDate);
        dashBoardDataList.add(dashBoardData);
        return dashBoardDataList;
    }

    private List<SalesDto.GetGraphData> convertToGraphData(List<SalesDto.GetGraphTmp> tblData){
        List<SalesDto.GetGraphData> graphData = new ArrayList<>();
        LocalDate localDate = tblData.get(0).getDate();
        double total = 0;
        List<SalesDto.AverageByType> averageByTypes = new ArrayList<>();

        for (SalesDto.GetGraphTmp tmpData : tblData) {
            if(!localDate.isEqual(tmpData.getDate())) {
                graphData.add(
                        SalesDto.GetGraphData.builder()
                                .date(localDate)
                                .totalAve(total)
                                .byTypes(averageByTypes)
                                .build()
                );
                total = 0;
                averageByTypes = new ArrayList<>();
                localDate = tmpData.getDate();
            }

            total += tmpData.getAve();
            averageByTypes.add(
                    SalesDto.AverageByType.builder()
                            .ave(tmpData.getAve())
                            .saleType(tmpData.getSaleType())
                            .build()
            );
        }
        graphData.add(
                SalesDto.GetGraphData.builder()
                        .date(localDate)
                        .totalAve(total)
                        .byTypes(averageByTypes)
                        .build()
        );
        return graphData;
    }

    @Getter
    private enum DayType{
        YEAR, MONTH, DAY
    }
}
