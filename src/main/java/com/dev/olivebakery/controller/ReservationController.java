package com.dev.olivebakery.controller;

import com.dev.olivebakery.domain.dto.ReservationDto;
import com.dev.olivebakery.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 * 1. 오늘 날짜 예약 정보만 가져오기.(state 별로 따로)
 * 2. 예약 번호 발급
 * 3. 예약할때 validation check
 * 4.
 */

@RestController
@RequestMapping(value = "/olive/reservation")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiOperation("user가 예약한 전체 내역 조회")
    @GetMapping("/{userId}")
    public List<ReservationDto.Get> getReservation(@PathVariable String userId){
        return reservationService.getReservationInfoByUserId(userId);
    }

    @ApiOperation("예약 정보 저장")
    @PostMapping
    public void saveReservation(@RequestBody ReservationDto.Save saveDto) {
        reservationService.saveReservation(saveDto);
    }

    @ApiOperation("예약 정보 수정")
    @PutMapping("/{num}")
    public void updateReservationType(@PathVariable("num") Long reservationId) {
        reservationService.updateReservationType(reservationId);
    }

    @ApiOperation("예약정보 삭제")
    @DeleteMapping("/{num}")
    public void deleteReservation(@PathVariable Long reservationId){
        reservationService.deleteReservation(reservationId);
    }


}
