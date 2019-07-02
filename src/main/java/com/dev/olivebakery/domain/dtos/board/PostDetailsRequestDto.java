package com.dev.olivebakery.domain.dtos.board;

import com.dev.olivebakery.domain.entity.Board;
import com.dev.olivebakery.domain.entity.Member;
import com.dev.olivebakery.domain.enums.BoardType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailsRequestDto {
    private String context;
    private String title;
    private BoardType boardType;
    @ApiModelProperty(notes = "true 또는 false로 보내야함")
    private String isNotice;
    @ApiModelProperty(notes = "true 또는 false로 보내야함")
    private String isSecret;

    @Builder
    public PostDetailsRequestDto(String context, String title, BoardType boardType, String isSecret, String isNotice) {
        this.context = context;
        this.title = title;
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
