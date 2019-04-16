package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.domain.entity.BreadImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BreadImageRepository extends JpaRepository<BreadImage, Long> {

    Optional<BreadImage> findByBread(Bread bread);
}
