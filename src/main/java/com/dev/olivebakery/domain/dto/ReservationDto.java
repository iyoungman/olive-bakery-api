package com.dev.olivebakery.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by YoungMan on 2019-03-06.
 * breadInfo : 빵이름, 개수
 */

public class ReservationDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Get{
        private Timestamp bringTime;
        private String userId;
        private LinkedHashMap<String, Integer> breadInfo;
        @Builder
        public Get(Timestamp bringTime, String userId, LinkedHashMap<String, Integer> breadInfo) {
            this.bringTime = bringTime;
            this.userId = userId;
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
    public static class Save {
        private Timestamp bringTime;
        private String userId;
        private LinkedHashMap<String, Integer> breadInfo;

        @Builder
        public Save(Timestamp bringTime, String userId, LinkedHashMap<String, Integer> breadInfo) {
            this.bringTime = bringTime;
            this.userId = userId;
            this.breadInfo = breadInfo;
        }

        public List<String> getBreadNames() {
            return new ArrayList<>(breadInfo.keySet());
        }

        public List<Integer> getBreadCounts() {
            return new ArrayList<>(breadInfo.values());
        }
    }
}
