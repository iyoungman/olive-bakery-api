package com.dev.olivebakery.service;

import com.dev.olivebakery.domain.dto.ReservationDto;
import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.domain.entity.Member;
import com.dev.olivebakery.domain.entity.Reservation;
import com.dev.olivebakery.domain.entity.ReservationInfo;
import com.dev.olivebakery.domain.enums.ReservationType;
import com.dev.olivebakery.exception.UserDefineException;
import com.dev.olivebakery.repository.ReservationInfoRepository;
import com.dev.olivebakery.repository.ReservationRepository;
import com.dev.olivebakery.utill.Explain;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungMan on 2019-02-09.
 */

@SuppressWarnings("Duplicates")
@Service
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final ReservationInfoRepository reservationInfoRepository;
	private SignService signService;
	private BreadService breadService;

	public ReservationService(ReservationRepository reservationRepository, ReservationInfoRepository reservationInfoRepository, SignService signService, BreadService breadService) {
		this.reservationRepository = reservationRepository;
		this.reservationInfoRepository = reservationInfoRepository;
		this.signService = signService;
		this.breadService = breadService;
	}

	public Reservation findById(Long reservationId) {
		return reservationRepository.findById(reservationId).orElseThrow(() -> new UserDefineException("해당 예약내역이 없습니다."));
	}

	@Explain("예약 정보 저장")
	public void saveReservation(ReservationDto.SaveRequest saveDto) {
		reservationInfoRepository.saveAll(convertSaveDtoToEntity(saveDto));
	}

	@Explain("saveReservation 의 서브함수")
	private List<ReservationInfo> convertSaveDtoToEntity(ReservationDto.SaveRequest saveDto) {

		List<ReservationInfo> reservationInfos = new ArrayList<>();
		Member member = signService.findById(saveDto.getUserEmail());
		List<Bread> breads = breadService.findsByNames(saveDto.getBreadNames());
		int finalPrice = breadService.getFinalPrice(saveDto.getBreadInfo());

		Reservation reservation = Reservation.builder()
				.bringTime(saveDto.getBringTime())
				.member(member)
				.price(finalPrice)
				.build();

		for (int i = 0; i < breads.size(); i++) {
			reservationInfos.add(ReservationInfo.builder()
					.breadCount(saveDto.getBreadCounts().get(i))
					.bread(breads.get(i))
					.reservation(reservation)
					.build());
		}
		return reservationInfos;
	}

	// TODO - 검토필요
	@Explain("예약 정보 수정")
	public void updateReservation(ReservationDto.UpdateRequest updateRequest) {
		deleteReservation(updateRequest.getReservationId());
		saveReservation(updateRequest.getSaveDto());
	}

	@Explain("예약 정보 삭제")
	public void deleteReservation(Long reservationId) {
		reservationRepository.deleteById(reservationId);
	}

	@Explain("예약 상태 수정")
	public void updateReservationType(Long reservationId) {
		Reservation reservation = findById(reservationId);
		reservation.updateReservationType();
		reservationRepository.save(reservation);
	}

	@Explain("유저의 모든 예약내역을 예약타입별로 가져옴 ")
	public List<ReservationDto.GetResponse> getReservationInfos(String email, ReservationType reservationType) {
		List<ReservationDto.GetTmp> getTmps = reservationRepository.getReservationInfos(email, reservationType);
		return convertGetTmpDtosToGetDtos(getTmps);
	}

	@Explain("유저의 가장 최근 예약내역을 예약타입에 무관하게 조회")
	public ReservationDto.GetResponse getReservationInfoByRecently(String email) {
		List<ReservationDto.GetTmp> getTmps = reservationRepository.getReservationInfoByRecently(email);
		return convertGetTmpDtoToGetDto(getTmps);
	}

	@Explain("날짜별 예약 조회, Admin 에서 사용")
	public List<ReservationDto.GetResponse> getReservationInfosByDate(ReservationDto.DateRequest dateRequest) {
		LocalDateTime startDate = dateRequest.getSelectDate().atStartOfDay();
		LocalDateTime endDate = dateRequest.getSelectDate().atTime(23, 59, 59);

		List<ReservationDto.GetTmp> getTmps = reservationRepository.getReservationInfosByDate(dateRequest.getReservationType(), startDate, endDate);
		return convertGetTmpDtosToGetDtos(getTmps);
	}

	@Explain("날짜구간별 예약 조회, Admin 에서 사용")
	public List<ReservationDto.GetResponse> getReservationInfosByDateRange(ReservationDto.DateRangeRequest dateRangeRequest) {
		LocalDateTime startDate = dateRangeRequest.getStartDate().atStartOfDay();
		LocalDateTime endDate = dateRangeRequest.getEndDate().atTime(23, 59, 59);

		List<ReservationDto.GetTmp> getTmps = reservationRepository.getReservationInfosByDate(dateRangeRequest.getReservationType(), startDate, endDate);
		return convertGetTmpDtosToGetDtos(getTmps);
	}

	// 수령 시간 정보 확인하는 메소드
	// 수령시간은 매일 저녁 8시 이전이여야 하며 예약시간보다 늦을 수는 없다.
	// 잘못된 수령 시간은 무조건 빠꾸시킴.
	// TODO 프런트에서 처리하는게 어떤지
	public void validationCheck(Timestamp bringTime) {

	}

	@Explain("GetTmp 를 GetResponse 로 변환")
	private ReservationDto.GetResponse convertGetTmpDtoToGetDto(List<ReservationDto.GetTmp> getTmps) {

		ReservationDto.GetResponse getResponse = ReservationDto.GetResponse.builder()
				.reservationId(getTmps.get(0).getReservationId())
				.reservationTime(getTmps.get(0).getReservationTime())
				.bringTime(getTmps.get(0).getBringTime())
				.price(getTmps.get(0).getPrice())
				.memberName(getTmps.get(0).getMemberName())
				.build();

		List<ReservationDto.ReservationBread> reservationBreads = new ArrayList<>();

		for (ReservationDto.GetTmp getTmp : getTmps) {
			reservationBreads.add(ReservationDto.ReservationBread.builder()
					.breadName(getTmp.getBreadName())
					.breadCount(getTmp.getBreadCount())
					.build()
			);
		}
		getResponse.setReservationBreads(reservationBreads);
		return getResponse;
	}

	@Explain("GetTmpDtos 를 GetDtos 로 변환")
	private List<ReservationDto.GetResponse> convertGetTmpDtosToGetDtos(List<ReservationDto.GetTmp> getTmps) {

		List<ReservationDto.GetResponse> getResponses = new ArrayList<>();

		Long reservationId = getTmps.get(0).getReservationId();
		LocalDateTime reservationTime = getTmps.get(0).getReservationTime();
		LocalDateTime bringTime = getTmps.get(0).getBringTime();
		int price = getTmps.get(0).getPrice();
		String memberName = getTmps.get(0).getMemberName();
		List<ReservationDto.ReservationBread> reservationBreads = new ArrayList<>();

		for (ReservationDto.GetTmp getTmp : getTmps) {
			if (reservationId.equals(getTmp.getReservationId())) {
				reservationBreads.add(
						ReservationDto.ReservationBread.builder()
								.breadName(getTmp.getBreadName())
								.breadCount(getTmp.getBreadCount())
								.build()
				);
			} else {
				getResponses.add(
						ReservationDto.GetResponse.builder()
								.reservationId(reservationId)
								.reservationTime(reservationTime)
								.bringTime(bringTime)
								.price(price)
								.memberName(memberName)
								.reservationBreads(reservationBreads)
								.build()
				);

				reservationBreads = new ArrayList<>();
				reservationId = getTmp.getReservationId();
				reservationTime = getTmp.getReservationTime();
				bringTime = getTmp.getBringTime();
				price = getTmp.getPrice();
				memberName = getTmp.getMemberName();
				reservationBreads.add(
						ReservationDto.ReservationBread.builder()
								.breadName(getTmp.getBreadName())
								.breadCount(getTmp.getBreadCount())
								.build()
				);
			}
		}

		getResponses.add(
				ReservationDto.GetResponse.builder()
						.reservationId(reservationId)
						.reservationTime(reservationTime)
						.bringTime(bringTime)
						.price(price)
						.memberName(memberName)
						.reservationBreads(reservationBreads)
						.build()
		);
		return getResponses;
	}
}
