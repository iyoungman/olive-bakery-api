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

    /**
     *
     * @param boardType: 게시판 or QnA
     * @param pageNum: 페이지 번호
     *
     */
    public Page<BoardDto.GetPosts> getPosts(BoardType boardType, int pageNum) {
        return boardRepository.getPosts(boardType,pageNum);
    }

    public List<BoardDto.GetPosts> getNoticePosts(){
        return boardRepository.getNoticePosts();
    }

    public BoardDto.GetPostDetails getPost(Long boardId){
        return boardRepository.getPostDetails(boardId);
    }

    public void saveBoard(BoardDto.SavePost savePostDto) {
        Member member = signService.findById(savePostDto.getUserId());
        boardRepository.save(savePostDto.toEntity(member));
    }

    public void updateBoard(BoardDto.UpdatePost updatePostDto) {
        Board board = findBoardById(updatePostDto.getBoardId());
        board.updateBoard(updatePostDto);
        boardRepository.save(board);
    }

    public void deleteBoard(Long boardId) {
        Board board = findBoardById(boardId);
        boardRepository.delete(board);
    }

    public void saveComment(CommentDto.SaveComment comment) {
        Board board = findBoardById(Long.valueOf(comment.getBoardId()));
        List<Comment> comments = board.getComments();
        comments.add(comment.toEntity());
        board.setComments(comments);
        boardRepository.save(board);
    }

    public void updateComment(CommentDto.UpdateComment updateComment) {
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

    public void deleteComment(Long commentId) {

    }
}
