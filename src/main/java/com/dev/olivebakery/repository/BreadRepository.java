package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.domain.enums.DayType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by YoungMan on 2019-02-09.
 */

public interface BreadRepository extends JpaRepository<Bread, Long> {

    List<Bread> findByDays(DayType dayType);

    Optional<Bread> findByName(String breadName);

    List<Bread> findByNameIn(List<String> breadName);
}
