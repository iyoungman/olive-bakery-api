package com.dev.olivebakery.domain.dto;

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

        @Builder
        public GetAverage(LocalDate date, double ave) {
            this.date = date;
            this.ave = ave;
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
