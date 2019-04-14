package com.dev.olivebakery.repository.custom;

import com.dev.olivebakery.domain.dto.SalesDto;

import java.time.LocalDate;
import java.util.List;

public interface SalesRepositoryCustom {
    List<SalesDto.GetAverage> getAverageSales(String dayType, LocalDate date);
    List<SalesDto.GetDashBoardData> getDashData();
}
