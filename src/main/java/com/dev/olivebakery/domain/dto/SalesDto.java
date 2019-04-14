package com.dev.olivebakery.domain.dto;

import com.dev.olivebakery.domain.enums.SaleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class SalesDto {

    @Getter
    @NoArgsConstructor
    public static class GetAverage{
        private LocalDate date;
        private double ave;
        private SaleType saleType;

        @Builder
        public GetAverage(LocalDate date, double ave, SaleType saleType) {
            this.date = date;
            this.ave = ave;
            this.saleType = saleType;
        }
    }

    @Getter
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

    public static class SaveSale{

    }
}
