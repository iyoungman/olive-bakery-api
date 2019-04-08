package com.dev.olivebakery.domain.dto;


import com.dev.olivebakery.domain.entity.Board;
import com.dev.olivebakery.domain.entity.Comment;
import com.dev.olivebakery.domain.entity.Member;
import com.dev.olivebakery.domain.enums.BoardType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BoardDto {
    @Getter @NoArgsConstructor
    public static class GetPosts {
        private Long boardId;
        private Timestamp insertTime;
        private Timestamp updateTime;
        private String title;
        private String context;
        private boolean isNotice;
        private boolean isSecret;
        private String userId;

        @Builder
        public GetPosts(Long boardId, Timestamp insertTime, Timestamp updateTime, String title, String context, boolean isNotice, boolean isSecret, String userId) {
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

    @Getter @NoArgsConstructor
    public static class GetPostDetails {
        private GetPosts posts;
        private List<CommentDto.Get> comments = new ArrayList<>();

        @Builder
        public GetPostDetails(GetPosts posts, List<CommentDto.Get> comments) {
            this.posts = posts;
            this.comments = comments;
        }

        public void setComments(List<CommentDto.Get> comments) {
            this.comments = comments;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Save {
        private String context;
        private String title;
        private String userId;
        private BoardType boardType;
        private String isNotice;
        private String isSecret;

        @Builder
        public Save(String context, String title, String userId, BoardType boardType, String isSecret, String isNotice) {
            this.context = context;
            this.title = title;
            this.userId = userId;
            this.boardType = boardType;
            this.isNotice = isNotice;
            this.isSecret = isSecret;
        }

        public Board toEntity(Member member) {
            return Board.builder()
                    .context(context)
                    .title(title)
                    .member(member)
                    .boardType(boardType)
                    .isNotice(isNotice.matches("true"))
                    .isSecret(isSecret.matches("true"))
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Update {
        private Long boardId;
        private String context;
        private String title;
        private String isNotice;
        private String isSecret;

        @Builder
        public Update(Long boardId, String context, String title, String isSecret, String isNotice) {
            this.boardId = boardId;
            this.context = context;
            this.title = title;
            this.isNotice = isNotice;
            this.isSecret = isSecret;
        }
    }
}
