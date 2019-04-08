package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByUserIdAndUpdateTime(String userId, LocalDateTime updateTime);
}
