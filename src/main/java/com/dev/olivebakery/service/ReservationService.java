package com.dev.olivebakery.service;

import com.dev.olivebakery.domain.dto.ReservationDto;
import com.dev.olivebakery.domain.entity.*;
import com.dev.olivebakery.exception.UserDefineException;
import com.dev.olivebakery.repository.ReservationInfoRepository;
import com.dev.olivebakery.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by YoungMan on 2019-02-09.
 */

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

    public void saveReservation(ReservationDto.Save saveDto) {
        reservationInfoRepository.saveAll(convertSaveDtoToEntity(saveDto));
    }

    private List<ReservationInfo> convertSaveDtoToEntity(ReservationDto.Save saveDto) {
        List<ReservationInfo> reservationInfos = new ArrayList<>();
        Member member = signService.findById(saveDto.getUserId());
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

    // 예약 정보 수정
    // 카카오톡(플러스)로 메세지 보내야함.(관리자에게)
    public void updateReservationType(Long reservationId) {
        Reservation reservation = findById(reservationId);
        reservation.updateReservationType();
        reservationRepository.save(reservation);
    }

    // 예약 정보 삭제
    // 카카오톡(플러스)로 메세지 보내야함.(관리자에게)
    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }


    // userId로 예약된 모든 정보 가져오기.
    // TODO Reservation Type의 따라 따로 보내줄것(정렬도 괜춘)
    public List<ReservationDto.Get> getReservationInfoByUserId(String userId) {
        List<Reservation> reservations = reservationRepository.findByMember(signService.findById(userId))
                .orElseThrow(() -> new UserDefineException("해당 아이디로 예약되어 있는 목록이 존재하지 않습니다."));

        List<ReservationDto.Get> getList = new ArrayList<>();
        reservations.forEach(reservation -> {
            LinkedHashMap<String , Integer> tmp = new LinkedHashMap<>();
            reservation.getReservationInfos().forEach(reservationInfo -> {
                tmp.put(reservationInfo.getBread().getName(), reservationInfo.getBreadCount());
            });
            getList.add(ReservationDto.Get.builder()
                    .userId(reservation.getMember().getEmail())
                    .bringTime(reservation.getBringTime())
                    .breadInfo(tmp)
                    .build());
        });

        return getList;
    }

    // 가장 최근에 예약한 1개 예약내역 가져오기.
    public void getReservationInfo(String userId){

    }

    // 현재 날짜로 예약된 예약정보 모두 가져오기
    // ReservaionType의 따라서 따로 보내줄것.(정렬도 괜춘) (Admin에서 사용)
    public void getReservaionInfoByDate(){

    }

    // 수령 시간 정보 확인하는 메소드
    // 수령시간은 매일 저녁 8시 이전이여야 하며 예약시간보다 늦을 수는 없다.
    // 잘못된 수령 시간은 무조건 빠꾸시킴.
    public void validationCheck(Timestamp bringTime){

    }
}
