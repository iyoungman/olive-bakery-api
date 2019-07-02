package com.dev.olivebakery.domain.dtos.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 게시판 또는 QnA 리스트 Dto
 */

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    private Long boardId;
    private LocalDateTime insertTime;
    private LocalDateTime updateTime;
    private String title;
    private String context;
    private boolean isNotice;
    private boolean isSecret;
    private String userId;

    @Builder
    public PostListResponseDto(Long boardId, LocalDateTime insertTime, LocalDateTime updateTime, String title, String context, boolean isNotice, boolean isSecret, String userId) {
        this.boardId = boardId;
        this.insertTime = insertTime;
        this.updateTime = updateTime;
        this.title = title;
        this.context = context;
        this.isNotice = isNotice;
        this.isSecret = isSecret;
        this.userId = userId;
    }
}
