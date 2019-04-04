package com.dev.olivebakery.repository.implement;

import com.dev.olivebakery.domain.dto.BoardDto;
import com.dev.olivebakery.domain.entity.Board;
import com.dev.olivebakery.domain.entity.QBoard;
import com.dev.olivebakery.domain.entity.QComment;
import com.dev.olivebakery.domain.entity.QMember;
import com.dev.olivebakery.domain.enums.BoardType;
import com.dev.olivebakery.repository.custom.BoardRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class BoardRepositoryImpl extends QuerydslRepositorySupport implements BoardRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    private QBoard board = QBoard.board;
    private QMember member = QMember.member;
    private QComment comment = QComment.comment;

    private int DEFAULT_LIMIT_SIZE = 10;

    public BoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Page<BoardDto.GetPosts> getBoards(BoardType boardType, int pageNum) {
        JPAQuery query = new JPAQuery(entityManager);
        long countResult = query.select(board.count()).from(board).fetchCount();

        JPAQuery<BoardDto.GetPosts> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(BoardDto.GetPosts.class, board.boardId, board.insertTime, board.updateTime, board.title, board.context, board.isNotice, board.isSecret, member.email))
                .from(board)
                .join(board.member, member)
                .where(board.boardType.eq(boardType))
                .orderBy(board.boardId.desc())
                .offset(--pageNum * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE)
        ;
        List<BoardDto.GetPosts> boards = jpaQuery.fetch();
        return new PageImpl<BoardDto.GetPosts>(boards, PageRequest.of(pageNum, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "boardId")), countResult);
    }

    @Override
    public BoardDto.GetPost getBoard(Long boardId) {
        JPAQuery<BoardDto.GetPost> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(BoardDto.GetPost.class, board.boardId, board.insertTime, board.updateTime, board.title, board.context, board.isNotice, board.isSecret, member.email, comment))
                .from(board)
                .join(board.member, member)
                .join(board.comments, comment)
                .where(board.boardId.eq(boardId));
        return jpaQuery.fetchOne();
    }
}
