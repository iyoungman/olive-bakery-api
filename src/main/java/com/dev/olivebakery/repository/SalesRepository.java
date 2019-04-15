package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.Sales;
import com.dev.olivebakery.domain.enums.SaleType;
import com.dev.olivebakery.repository.custom.SalesRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales, Long>, SalesRepositoryCustom {
    Optional<Sales> findByDateEqualsAndSaleType(LocalDate date, SaleType saleType);
}
