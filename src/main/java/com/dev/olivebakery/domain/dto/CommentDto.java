package com.dev.olivebakery.domain.dto;

import com.dev.olivebakery.domain.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class CommentDto {

    @Getter
    @NoArgsConstructor
    public static class Save{
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
        public Save(String boardId, String userName, String content) {
            this.boardId = boardId;
            this.userName = userName;
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Update{
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
