package com.dev.olivebakery.controller;

import com.dev.olivebakery.domain.dto.BoardDto;
import com.dev.olivebakery.domain.dto.CommentDto;
import com.dev.olivebakery.domain.enums.BoardType;
import com.dev.olivebakery.service.BoardService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 * 1. 게시물 하나씩 가져오기.
 * 2. 게시물
 */

@RestController
@RequestMapping(value = "/olive/board")
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @ApiOperation("게시판과 QnA의 게시물들 불러오기")
    @GetMapping("/{type}/page/{num}")
    public Page<BoardDto.GetPosts> getBoards(@PathVariable("type") BoardType boardType, @PathVariable("num") int pageNum) {
        return boardService.getPosts(boardType, pageNum);
    }

    @ApiOperation("게시물 하나 불러오기")
    @GetMapping("/id/{boardId}")
    public BoardDto.GetPost getPost(@PathVariable("boardId") Long boardId){
        return boardService.getPost(boardId);
    }

    @PostMapping
    public void saveBoard(@RequestBody BoardDto.Save saveDto) {
        boardService.saveBoard(saveDto);
    }

    @PutMapping
    public void updateBoard(@RequestBody BoardDto.Update updateDto) {
        boardService.updateBoard(updateDto);
    }

    @DeleteMapping("/{num}")
    public void deleteBoard(@PathVariable("num") Long boardId) {
        boardService.deleteBoard(boardId);
    }

    @PostMapping("/comment")
    public void saveComment(@RequestBody CommentDto.Save comment){
        boardService.saveComment(comment);
    }

    @PutMapping("/comment")
    public void updateComment(@RequestBody CommentDto.Update comment){
        boardService.updateComment(comment);
    }
}
