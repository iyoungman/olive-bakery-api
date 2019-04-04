package com.dev.olivebakery.service;

import com.dev.olivebakery.domain.dto.BoardDto;
import com.dev.olivebakery.domain.dto.CommentDto;
import com.dev.olivebakery.domain.entity.Board;
import com.dev.olivebakery.domain.entity.Comment;
import com.dev.olivebakery.domain.entity.Member;
import com.dev.olivebakery.domain.enums.BoardType;
import com.dev.olivebakery.exception.UserDefineException;
import com.dev.olivebakery.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final SignService signService;

    public BoardService(BoardRepository boardRepository, SignService signService) {
        this.boardRepository = boardRepository;
        this.signService = signService;
    }

    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new UserDefineException("해당 번호의 게시글은 존재하지 않습니다."));
    }

    /*
     * 공지 or 질문 게시판 타입에 맞게 페이징
     */
    public Page<BoardDto.GetPosts> getPosts(BoardType boardType, int pageNum) {
//        Pageable pageable = PageRequest.of(pageNum - 1, 10, new Sort(new Sort.Order(Sort.Direction.DESC, "boardId"), new Sort.Order(Sort.Direction.DESC, "isNotice")));
        Page<BoardDto.GetPosts> byBoardType = boardRepository.getPosts(boardType,pageNum);
        return byBoardType;
    }

    public BoardDto.GetPostDetails getPost(Long boardId){
        return boardRepository.getPostDetails(boardId);
    }

    public void saveBoard(BoardDto.Save saveDto) {
        Member member = signService.findById(saveDto.getUserId());

        Board board = saveDto.toEntity(member);
        boardRepository.save(board);
    }

    public void updateBoard(BoardDto.Update updateDto) {
        Board board = findBoardById(updateDto.getBoardId());
        board.updateBoard(updateDto);
        boardRepository.save(board);
    }

    public void deleteBoard(Long boardId) {
        Board board = findBoardById(boardId);
        boardRepository.delete(board);
    }

    public void saveComment(CommentDto.Save comment) {
        Board board = findBoardById(Long.valueOf(comment.getBoardId()));
        List<Comment> comments = board.getComments();
        comments.add(comment.toEntity());
        board.setComments(comments);
        boardRepository.save(board);
    }

    public void updateComment(CommentDto.Update updateComment) {
        Board board = findBoardById(Long.valueOf(updateComment.getBoardId()));
        List<Comment> comments = board.getComments();
        comments.forEach(comment->{
            if(Long.valueOf(updateComment.getCommentId()).equals(comment.getCommentId())){
                if(!comment.getUserName().equals(updateComment.getUserName()))
                    throw new UserDefineException("수정은 작성자만 가능합니다.");

                comment.setContent(updateComment.getContent());
            }
        });
        board.setComments(comments);
        boardRepository.save(board);
    }

}
