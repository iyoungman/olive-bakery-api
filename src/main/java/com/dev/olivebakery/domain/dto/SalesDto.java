package com.dev.olivebakery.domain.dto;

import com.dev.olivebakery.domain.enums.SaleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class SalesDto {

    @Getter
    @NoArgsConstructor
    public static class GetGraphTmp {
        private LocalDate date;
        private double ave;
        private SaleType saleType;

        @Builder
        public GetGraphTmp(LocalDate date, double ave, SaleType saleType) {
            this.date = date;
            this.ave = ave;
            this.saleType = saleType;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class GetGraphData{
        private LocalDate date;
        private double totalAve;

        List<AverageByType> byTypes;

        @Builder
        public GetGraphData(LocalDate date, double totalAve, List<AverageByType> byTypes) {
            this.date = date;
            this.totalAve = totalAve;
            this.byTypes = byTypes;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class AverageByType{
        private double ave;
        private SaleType saleType;

        @Builder
        public AverageByType(double ave, SaleType saleType) {
            this.ave = ave;
            this.saleType = saleType;
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class GetDashBoardData{
        private LocalDate date;
        private int reservationCnt;
        private int reservationSale;
        private int offlineSale;
        private int totalSale;

        @Builder
        public GetDashBoardData(LocalDate date, int reservationCnt, int reservationSale, int offlineSale, int totalSale) {
            this.date = date;
            this.reservationCnt = reservationCnt;
            this.reservationSale = reservationSale;
            this.offlineSale = offlineSale;
            this.totalSale = totalSale;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class GetDashBoardTmp{
        private LocalDate date;
        private int sales;
        private int reservationCnt;
        private SaleType saleType;

        public GetDashBoardTmp(LocalDate date, int sales, int reservationCnt, SaleType saleType) {
            this.date = date;
            this.sales = sales;
            this.reservationCnt = reservationCnt;
            this.saleType = saleType;
        }
    }

    public static class SaveSale{

    }
}
