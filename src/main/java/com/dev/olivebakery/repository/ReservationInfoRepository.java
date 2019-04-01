package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.ReservationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by YoungMan on 2019-02-09.
 */

public interface ReservationInfoRepository extends JpaRepository<ReservationInfo, Long> {
}
