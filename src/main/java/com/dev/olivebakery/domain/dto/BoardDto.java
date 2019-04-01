package com.dev.olivebakery.domain.dto;


import com.dev.olivebakery.domain.entity.Board;
import com.dev.olivebakery.domain.entity.Member;
import com.dev.olivebakery.domain.enums.BoardType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardDto {

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
