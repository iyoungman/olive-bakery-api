package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.SoldOut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SoldOutRepository extends JpaRepository<SoldOut, Long> {
    List<SoldOut> findByDateEquals(LocalDate date);
}
