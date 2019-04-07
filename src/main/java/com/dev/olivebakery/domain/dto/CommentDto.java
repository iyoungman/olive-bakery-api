package com.dev.olivebakery.domain.dto;

import com.dev.olivebakery.domain.entity.Comment;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public class CommentDto {
    @ApiModel(value = "댓글 가져오기")
    @Getter
    @NoArgsConstructor
    public static class GetComment {
        private LocalDateTime insertTime;
        private LocalDateTime updateTime;
        private String userName;
        private String content;

        @Builder
        public GetComment(LocalDateTime insertTime, LocalDateTime updateTime, String userName, String content) {
            this.insertTime = insertTime;
            this.updateTime = updateTime;
            this.userName = userName;
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SaveComment {
        private String boardId;
        private String userName;
        private String content;

        public Comment toEntity(){
            return Comment.builder()
                    .userName(userName)
                    .content(content)
                    .build();
        }
        @Builder
        public SaveComment(String boardId, String userName, String content) {
            this.boardId = boardId;
            this.userName = userName;
            this.content = content;
        }

    }
    @Getter
    @NoArgsConstructor
    public static class UpdateComment {
        private String boardId;
        private String commentId;
        private String userName;

        private String content;

        public Comment toEntity(){
            return Comment.builder()
                    .userName(userName)
                    .content(content)
                    .build();
        }
    }
}
