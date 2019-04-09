package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {

}
