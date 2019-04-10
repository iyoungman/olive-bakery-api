package com.dev.olivebakery.service;

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

import static com.dev.olivebakery.domain.dto.ReservationDto.*;

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
	public void saveReservation(SaveRequest saveDto) {
		reservationInfoRepository.saveAll(convertSaveDtoToEntity(saveDto));
	}

	@Explain("saveReservation 의 서브함수")
	private List<ReservationInfo> convertSaveDtoToEntity(SaveRequest saveDto) {

		List<ReservationInfo> reservationInfos = new ArrayList<>();
		List<Bread> breads = breadService.findsByNames(saveDto.getBreadNames());
		Member member = signService.findById(saveDto.getUserEmail());
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
					.build()
			);
		}
		return reservationInfos;
	}

	// TODO - 검토필요
	@Explain("예약 정보 수정")
	public void updateReservation(UpdateRequest updateRequest) {
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
	public List<GetResponse> getReservationInfos(String email, ReservationType reservationType) {
		List<GetTemp> getTemps = reservationRepository.getReservationInfos(email, reservationType);
		return convertGetTempDtoListToGetDtoList(getTemps);
	}

	@Explain("유저의 가장 최근 예약내역을 예약타입에 무관하게 조회")
	public GetResponse getReservationInfoByRecently(String email) {
		List<GetTemp> getTemps = reservationRepository.getReservationInfoByRecently(email);
		return convertGetTmpDtoToGetDto(getTemps);
	}

	@Explain("날짜별 예약 조회, Admin 에서 사용")
	public List<GetResponse> getReservationInfosByDate(DateRequest dateRequest) {
		LocalDateTime startDate = dateRequest.getSelectDate().atStartOfDay();
		LocalDateTime endDate = dateRequest.getSelectDate().atTime(23, 59, 59);

		List<GetTemp> getTemps = reservationRepository.getReservationInfosByDate(dateRequest.getReservationType(), startDate, endDate);
		return convertGetTempDtoListToGetDtoList(getTemps);
	}

	@Explain("날짜구간별 예약 조회, Admin 에서 사용")
	public List<GetResponse> getReservationInfosByDateRange(DateRangeRequest dateRangeRequest) {
		LocalDateTime startDate = dateRangeRequest.getStartDate().atStartOfDay();
		LocalDateTime endDate = dateRangeRequest.getEndDate().atTime(23, 59, 59);

		List<GetTemp> getTemps = reservationRepository.getReservationInfosByDate(dateRangeRequest.getReservationType(), startDate, endDate);
		return convertGetTempDtoListToGetDtoList(getTemps);
	}

	// 수령 시간 정보 확인하는 메소드
	// 수령시간은 매일 저녁 8시 이전이여야 하며 예약시간보다 늦을 수는 없다.
	// 잘못된 수령 시간은 무조건 빠꾸시킴.
	// TODO 프런트에서 처리하는게 어떤지
	public void validationCheck(Timestamp bringTime) {

	}

	@Explain("GetTemp 를 GetResponse 로 변환")
	private GetResponse convertGetTmpDtoToGetDto(List<GetTemp> getTemps) {

		List<ReservationBread> reservationBreads = new ArrayList<>();

		for (GetTemp getTemp : getTemps) {
			reservationBreads.add(ReservationBread.build(getTemp));
		}
		return GetResponse.build(getTemps.get(0), reservationBreads);
	}

	@Explain("GetTempDto List 를 GetDto List 로 변환")
	public List<GetResponse> convertGetTempDtoListToGetDtoList(List<GetTemp> getTemps) {

		List<GetResponse> getResponses = new ArrayList<>();
		List<ReservationBread> reservationBreads = new ArrayList<>();
		Long reservationId = getTemps.get(0).getReservationId();

		for (GetTemp getTemp : getTemps) {
			if (getTemp.getReservationId().equals(reservationId)) {
				reservationBreads.add(ReservationBread.build(getTemp));

				if (getTemps.indexOf(getTemp) == getTemps.size() - 1) {
					getResponses.add(GetResponse.build(getTemps.get(getTemps.indexOf(getTemp)), reservationBreads));
				}
				continue;
			}
			getResponses.add(GetResponse.build(getTemps.get(getTemps.indexOf(getTemp) - 1), reservationBreads));

			reservationId = getTemp.getReservationId();
			reservationBreads = new ArrayList<>();
			reservationBreads.add(ReservationBread.build(getTemp));

			if (getTemps.indexOf(getTemp) == getTemps.size() - 1) {
				getResponses.add(GetResponse.build(getTemps.get(getTemps.indexOf(getTemp)), reservationBreads));
			}
		}
		return getResponses;
	}
}