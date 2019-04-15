package com.dev.olivebakery;

import com.dev.olivebakery.domain.entity.Board;
import com.dev.olivebakery.domain.entity.Comment;
import com.dev.olivebakery.domain.entity.Member;
import com.dev.olivebakery.domain.enums.BoardType;
import com.dev.olivebakery.repository.BoardRepository;
import com.dev.olivebakery.repository.CommentRepository;
import com.dev.olivebakery.repository.MemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Commit
public class OliveBakeryApplicationTests {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

/*    @Before
    public void insertMember(){
        memberRepository.save(
                Member.builder()
                        .email("chunso@email.com")
                        .name("박춘소")
                        .phoneNumber("010-9128-4939")
                        .pw("12345")
                        .stamp(0)
                        .build()
        );
    }*/


    @Test
    public void insertBoard() {
        /*for (int i = 1; i <= 30; i++) {
            Board savedBoard = boardRepository.save(
                                Board.builder()
                                        .boardType(BoardType.BOARD)
                                        .context(i + "번째 게시물입니다")
                                        .title(i + "번째 게시물")
                                        .member(memberRepository.findByEmail("chunso@email.com").get())
                                        .isNotice(i % 5 == 0)
                                        .isSecret(false)
                                        .build()
                                );

            for (int j = 0; j < 10; j++) {
                commentRepository.save(
                        Comment.builder()
                                .content(i + "번째 게시물 댓글" + j)
                                .userName("박춘소")
                                .board(savedBoard)
                                .build()
                );
            }

        }*/
    }


}
