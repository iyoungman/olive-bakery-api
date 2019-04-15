package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.domain.entity.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {

    @Query("delete from Ingredients i where i.bread = :bread")
    void deleteAllByBread(@Param(value = "bread") Bread bread);
}
