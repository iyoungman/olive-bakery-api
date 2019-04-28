package com.dev.olivebakery.domain.dto;

import com.dev.olivebakery.domain.entity.Sales;
import com.dev.olivebakery.domain.enums.ReservationType;
import com.dev.olivebakery.domain.enums.SaleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by YoungMan on 2019-03-06.
 * breadInfo : 빵이름, 개수
 */

@SuppressWarnings("Duplicates")
public class ReservationDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

		public static GetResponse build(GetTemp getTemp, List<ReservationBread> reservationBreads) {
			return GetResponse.builder()
					.reservationId(getTemp.getReservationId())
					.reservationTime(getTemp.getReservationTime())
					.bringTime(getTemp.getBringTime())
					.price(getTemp.getPrice())
					.memberName(getTemp.getMemberName())
					.reservationBreads(reservationBreads)
					.build();
		}

		@Override
		public String toString() {
			return "GetResponse{" +
					"reservationId=" + reservationId +
					", reservationTime=" + reservationTime +
					", bringTime=" + bringTime +
					", price=" + price +
					", memberName='" + memberName + '\'' +
					", reservationBreads=" + reservationBreads +
					'}';
		}
	}


	@Getter
	@NoArgsConstructor(access = AccessLevel.PUBLIC)
	public static class GetTemp {

		private Long reservationId;
		private LocalDateTime reservationTime;
		private LocalDateTime bringTime;
		private int price;
		private String memberName;
		private String breadName;
		private int breadCount;

		@Builder
		public GetTemp(Long reservationId, LocalDateTime reservationTime, LocalDateTime bringTime, int price, String memberName, String breadName, int breadCount) {
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
			return "GetTemp{" +
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

		public static ReservationBread build(GetTemp getTemp) {
			return ReservationBread.builder()
					.breadName(getTemp.getBreadName())
					.breadCount(getTemp.getBreadCount())
					.build();
		}

		@Override
		public String toString() {
			return "ReservationBread{" +
					"breadName='" + breadName + '\'' +
					", breadCount=" + breadCount +
					'}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			ReservationBread that = (ReservationBread) o;
			return breadCount == that.breadCount &&
					Objects.equals(breadName, that.breadName);
		}

		@Override
		public int hashCode() {
			return Objects.hash(breadName, breadCount);
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

		@ApiModelProperty(notes = "2019-04-14 같은 형태.")
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

		@ApiModelProperty(notes = "2019-04-14 같은 형태.")
		private LocalDate startDate;

		@ApiModelProperty(notes = "2019-04-14 같은 형태.")
		private LocalDate endDate;

		@Builder
		public DateRangeRequest(ReservationType reservationType, LocalDate startDate, LocalDate endDate) {
			this.reservationType = reservationType;
			this.startDate = startDate;
			this.endDate = endDate;
		}
	}


	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ReservationSale {

		private long reservationCount;
		private long reservationSales;
		private LocalDate date;

		@Builder
		public ReservationSale(long reservationCount, long reservationSales) {
			this.reservationCount = reservationCount;
			this.reservationSales = reservationSales;
			this.date = LocalDate.now();
		}

		public Sales toEntity(){
			return Sales.builder()
					.date(date)
					.reservationCnt((int) reservationCount)
					.sales((int) reservationSales)
					.saleType(SaleType.RESERVATION)
					.build();
		}
	}


}
