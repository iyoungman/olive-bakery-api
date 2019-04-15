package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.Days;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DaysRepository extends JpaRepository<Days, Long> {
}
