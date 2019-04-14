package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.Sales;
import com.dev.olivebakery.repository.custom.SalesRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long>, SalesRepositoryCustom {
}
