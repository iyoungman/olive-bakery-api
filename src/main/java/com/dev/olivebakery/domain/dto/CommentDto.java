package com.dev.olivebakery.domain.dto;

import com.dev.olivebakery.domain.entity.Comment;
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
