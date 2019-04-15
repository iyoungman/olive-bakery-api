package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.domain.entity.Days;
import com.dev.olivebakery.domain.enums.DayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DaysRepository extends JpaRepository<Days, Long> {

    @Query("select d.bread from Days d where d.dayType = :dayType")
    List<Bread> findByDayType(@Param(value="dayType")DayType dayType);

}
