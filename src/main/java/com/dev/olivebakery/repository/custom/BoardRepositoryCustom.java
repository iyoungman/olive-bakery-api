package com.dev.olivebakery.repository.custom;

import com.dev.olivebakery.domain.dto.BoardDto;
import com.dev.olivebakery.domain.enums.BoardType;
import org.springframework.data.domain.Page;

public interface BoardRepositoryCustom {
    Page<BoardDto.GetPosts> getPosts(BoardType boardType, int pageNum);
    BoardDto.GetPostDetails getPostDetails(Long boardId);
}
