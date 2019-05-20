package com.dev.olivebakery.service;

import com.dev.olivebakery.domain.dto.ReservationDto;
import com.dev.olivebakery.domain.entity.Member;
import com.dev.olivebakery.domain.enums.ReservationType;
import com.dev.olivebakery.exception.UserDefineException;
import com.dev.olivebakery.repository.BreadRepository;
import com.dev.olivebakery.repository.MemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {

	@Autowired
	ReservationService reservationService;

	@MockBean(name = "memberRepository")
	MemberRepository memberRepository;

	@MockBean(name = "breadRepository")
	BreadRepository breadRepository;


	@Before
	@Transactional
	public void setUp() throws Exception {

		Member member = new Member();
		given(memberRepository.findByEmail(any()))
				.willReturn(java.util.Optional.of(member));

		LocalDateTime bringTime = LocalDate.now()
				.plusDays(1)
				.atTime(10, 10, 10)
		;

		ReservationDto.ReservationSaveRequest reservationSaveRequest = ReservationDto.ReservationSaveRequest.builder()
				.bringTime(bringTime)
				.userEmail("chunso@email.com")
				.breadInfo(Arrays.asList(
						ReservationDto.ReservationBread.builder()
								.breadName("소보로빵")
								.breadCount(1)
								.build(),
						ReservationDto.ReservationBread.builder()
								.breadName("죽빵")
								.breadCount(1)
								.build()
						)

				)
				.build()
		;

		reservationService.saveReservation(reservationSaveRequest);
	}


	@Test
	@Transactional
	public void getReservationInfos_예약목록_조회() throws Exception {

		//given
		final String email = "chunso@email.com";
		final ReservationType reservationType = ReservationType.REQUEST;
		List<ReservationDto.ReservationBread> expectedBreads = Arrays.asList(
				new ReservationDto.ReservationBread("소보로빵", 1),
				new ReservationDto.ReservationBread("죽빵", 1)
		);

		//when
		List<ReservationDto.ReservationResponse> reservationResponses = reservationService.getReservationInfos(email, reservationType);

		//then
		assertThat(reservationResponses.get(0).getReservationBreads().size(), is(2));
		assertThat(reservationResponses.get(0).getPrice(), is(30000));
		assertThat(reservationResponses.get(0).getReservationBreads(), is(expectedBreads));
	}


	@Test
	@Transactional
	public void getReservationInfoRecently_최신예약_한건_조회() throws Exception {

		//given
		final String email = "chunso@email.com";
		List<ReservationDto.ReservationBread> expectedBreads = Arrays.asList(
				new ReservationDto.ReservationBread("소보로빵", 1),
				new ReservationDto.ReservationBread("죽빵", 1)
		);

		//when
		ReservationDto.ReservationResponse reservationResponse = reservationService.getReservationInfoByRecently(email);

		//then
		assertThat(reservationResponse.getReservationBreads().size(), is(2));
		assertThat(reservationResponse.getPrice(), is(30000));
		assertThat(reservationResponse.getReservationBreads(), is(expectedBreads));
	}


	@Test
	@Transactional
	public void getReservationInfosByDate_선택날짜_예약목록_조회() throws Exception {

		//given
		ReservationDto.ReservationDateRequest reservationDateRequestDto = ReservationDto.ReservationDateRequest.builder()
				.reservationType(ReservationType.REQUEST)
				.selectDate(LocalDate.of(2019, 5, 6))
				.build();

		//when
		List<ReservationDto.ReservationResponse> reservationRespons = reservationService.getReservationInfosByDate(reservationDateRequestDto);

		//then
		reservationRespons.stream().forEach(s -> System.out.println(s.toString()));
	}


	@Test
	@Transactional
	public void getReservationInfosByDateRange_기간날짜_예약목록_조회() throws Exception {

		//given
		ReservationDto.ReservationDateRangeRequest reservationDateRangeRequest = ReservationDto.ReservationDateRangeRequest.builder()
				.reservationType(ReservationType.REQUEST)
				.startDate(LocalDate.of(2019, 3, 24))
				.endDate(LocalDate.of(2019, 3, 30))
				.build();

		//when
		List<ReservationDto.ReservationResponse> getDtos = reservationService.getReservationInfosByDateRange(reservationDateRangeRequest);

		//then
		getDtos.stream().forEach(s -> System.out.println(s.toString()));
	}


	@Test
	@Transactional
	public void timeValidationCheck_올바른_예약시간() throws Exception {

		//given
		LocalDateTime bringTime = LocalDate.now()
				.plusDays(1)
				.atTime(12, 12, 12)
		;

		//then
		reservationService.timeValidationCheck(bringTime);
	}


	@Test(expected = UserDefineException.class)
	@Transactional
	public void timeValidationCheck_잘못된_예약시간_20시이후() throws Exception {

		//given
		LocalDateTime bringTime = LocalDate.now()
				.plusDays(1)
				.atTime(20, 12, 12)
		;

		//then
		reservationService.timeValidationCheck(bringTime);
	}

}
