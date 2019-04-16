package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.Board;
import com.dev.olivebakery.domain.enums.BoardType;
import com.dev.olivebakery.repository.custom.BoardRepositoryCustom;
import com.dev.olivebakery.repository.implement.BoardRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {


}
