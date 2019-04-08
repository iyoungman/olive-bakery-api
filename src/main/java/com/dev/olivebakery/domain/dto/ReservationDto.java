package com.dev.olivebakery.domain.dto;

import com.dev.olivebakery.domain.enums.ReservationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by YoungMan on 2019-03-06.
 * breadInfo : 빵이름, 개수
 */

@SuppressWarnings("Duplicates")
public class ReservationDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString(exclude = "reservationBreads")
    public static class GetResponse {

        private Long reservationId;

		@JsonFormat(pattern = "YYYY-MM-dd HH:mm")
        private LocalDateTime reservationTime;

		@JsonFormat(pattern = "YYYY-MM-dd HH:mm")
        private LocalDateTime bringTime;
        private int price;
        private String memberName;
        private List<ReservationBread> reservationBreads = new ArrayList<>();

        @Builder
        public GetResponse(Long reservationId, LocalDateTime reservationTime, LocalDateTime bringTime, int price, String memberName, List<ReservationBread> reservationBreads) {
            this.reservationId = reservationId;
            this.reservationTime = reservationTime;
            this.bringTime = bringTime;
            this.price = price;
            this.memberName = memberName;
            this.reservationBreads = reservationBreads;
        }

        public void setReservationBreads(List<ReservationBread> reservationBreads) {
            this.reservationBreads = reservationBreads;
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class GetTmp {

        private Long reservationId;
        private LocalDateTime reservationTime;
        private LocalDateTime bringTime;
        private int price;
        private String memberName;
        private String breadName;
        private int breadCount;

        @Builder
        public GetTmp(Long reservationId, LocalDateTime reservationTime, LocalDateTime bringTime, int price, String memberName, String breadName, int breadCount) {
            this.reservationId = reservationId;
            this.reservationTime = reservationTime;
            this.bringTime = bringTime;
            this.price = price;
            this.memberName = memberName;
            this.breadName = breadName;
            this.breadCount = breadCount;
        }


        @Override
        public String toString() {
            return "GetTmp{" +
                    "reservationId=" + reservationId +
                    ", reservationTime=" + reservationTime +
                    ", bringTime=" + bringTime +
                    ", price=" + price +
                    ", memberName='" + memberName + '\'' +
                    ", breadName='" + breadName + '\'' +
                    ", breadCount=" + breadCount +
                    '}';
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReservationBread {

        private String breadName;
        private int breadCount;

        @Builder
        public ReservationBread(String breadName, int breadCount) {
            this.breadName = breadName;
            this.breadCount = breadCount;
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveRequest {

        private LocalDateTime bringTime;
        private String userEmail;
        private LinkedHashMap<String, Integer> breadInfo;

        @Builder
        public SaveRequest(LocalDateTime bringTime, String userEmail, LinkedHashMap<String, Integer> breadInfo) {
            this.bringTime = bringTime;
            this.userEmail = userEmail;
            this.breadInfo = breadInfo;
        }

        public List<String> getBreadNames() {
            return new ArrayList<>(breadInfo.keySet());
        }

        public List<Integer> getBreadCounts() {
            return new ArrayList<>(breadInfo.values());
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateRequest {

        private Long reservationId;
        private SaveRequest saveDto;

        @Builder
        public UpdateRequest(Long reservationId, SaveRequest saveDto) {
            this.reservationId = reservationId;
            this.saveDto = saveDto;
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DateRequest {

        private ReservationType reservationType;
        private LocalDate selectDate;

        @Builder
        public DateRequest(ReservationType reservationType, LocalDate selectDate) {
            this.reservationType = reservationType;
            this.selectDate = selectDate;
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DateRangeRequest {

        private ReservationType reservationType;
        private LocalDate startDate;
        private LocalDate endDate;

        @Builder
        public DateRangeRequest(ReservationType reservationType, LocalDate startDate, LocalDate endDate) {
            this.reservationType = reservationType;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }


}
