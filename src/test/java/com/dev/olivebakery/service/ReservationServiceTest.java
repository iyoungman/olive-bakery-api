package com.dev.olivebakery.service;

import com.dev.olivebakery.domain.dto.ReservationDto;
import com.dev.olivebakery.domain.enums.ReservationType;
import com.dev.olivebakery.repository.ReservationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by YoungMan on 2019-04-08.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	ReservationService reservationService;

	@Test
	public void saveReservation() throws Exception {

		//given
		LinkedHashMap<String, Integer> maps = new LinkedHashMap<>();
		maps.put("생일빵", 4);//빵-개수
		maps.put("죽빵", 6);//빵-개수

		LocalDateTime bringTime = LocalDateTime.now();

		ReservationDto.SaveRequest saveRequest = ReservationDto.SaveRequest.builder()
				.bringTime(bringTime)
				.userEmail("testemail")
				.breadInfo(maps)
				.build();

		//when
		reservationService.saveReservation(saveRequest);
	}

	@Test
	public void updateReservationType() {
	}

	@Test
	public void getReservationInfos() throws Exception {

		//given
		final String email = "testemail";
		final ReservationType reservationType = ReservationType.REQUEST;

		//when
		List<ReservationDto.GetResponse> getResponses = reservationService.getReservationInfos(email, reservationType);

		//then
		getResponses.stream().forEach(s -> System.out.println(s.toString()));
	}

	@Test
	public void getReservationInfoRecently() throws Exception {

		//given
		final String email = "testemail";

		//when
		ReservationDto.GetResponse getResponse = reservationService.getReservationInfoByRecently(email);

		//then
		System.out.println(getResponse.toString());
	}

	@Test
	public void getReservationInfosByDate() throws Exception {

		//given
		ReservationDto.DateRequest dateRequestDto = ReservationDto.DateRequest.builder()
				.reservationType(ReservationType.REQUEST)
				.selectDate(LocalDate.of(2019, 3, 24))
				.build();

		//when
		List<ReservationDto.GetResponse> getResponses = reservationService.getReservationInfosByDate(dateRequestDto);

		//then
		getResponses.stream().forEach(s -> System.out.println(s.toString()));
	}

	@Test
	public void getReservationInfosByDateRange() throws Exception {

		//given
		ReservationDto.DateRangeRequest dateRangeRequest = ReservationDto.DateRangeRequest.builder()
				.reservationType(ReservationType.REQUEST)
				.startDate(LocalDate.of(2019, 3, 24))
				.endDate(LocalDate.of(2019, 3, 30))
				.build();

		//when
		List<ReservationDto.GetResponse> getDtos = reservationService.getReservationInfosByDateRange(dateRangeRequest);

		//then
		getDtos.stream().forEach(s -> System.out.println(s.toString()));
	}
}