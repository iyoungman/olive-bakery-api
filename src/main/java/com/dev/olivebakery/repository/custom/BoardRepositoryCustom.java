package com.dev.olivebakery.repository.custom;

import com.dev.olivebakery.domain.dto.BoardDto;
import com.dev.olivebakery.domain.enums.BoardType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardRepositoryCustom {
    Page<BoardDto.GetPosts> getPosts(BoardType boardType, int pageNum);
    List<BoardDto.GetPosts> getNoticePosts();
    BoardDto.GetPostDetails getPostDetails(Long boardId);
}
