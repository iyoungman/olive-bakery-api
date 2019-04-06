package com.dev.olivebakery.repository;

import com.dev.olivebakery.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
