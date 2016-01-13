package com.ex.post.facto.controller;

import com.ex.post.facto.model.Board;
import com.ex.post.facto.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Controller that handles all the CRUD operations regarding the retrospect boards.
 */
@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    /**
     * Creates a new board. Returns the URL of the newly created resource in the response header.
     * @param board
     * @return
     */
    @RequestMapping(value = "/board", method = RequestMethod.POST)
    public ResponseEntity<?> createBoard(@RequestBody Board board) {

        Board newBoard = boardService.saveBoard(board);
        HttpHeaders responseHeaders = getHttpHeaders(newBoard);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/board", method = RequestMethod.GET)
    public ResponseEntity<?> getAllBoards() {

        Iterable<Board> boards = boardService.findAll();

        for (Board board : boards) {
            board.add(linkTo(methodOn(BoardController.class).getBoard(board.getBoardId())).withSelfRel());
        }

        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    private HttpHeaders getHttpHeaders(Board newBoard) {
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newBoard.getBoardId()).toUri();
        responseHeaders.setLocation(newPollUri);
        return responseHeaders;
    }

    /**
     * Retrieves a board for the id sent as a path variable.
     * @param id
     * @return
     */
    @RequestMapping(value = "/board/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getBoard(@PathVariable Integer id) {

        Board board = boardService.findOne(id);

        ResponseEntity<?> response;
        if(board != null) {
            board.add(linkTo(methodOn(BoardController.class).getBoard(board.getBoardId())).withSelfRel());
            response = getFoundBoardResponse(board);
        } else {
            response = getNotoundBoardResponse();
        }

        return response;
    }

    private ResponseEntity getFoundBoardResponse(Board board) {
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    private ResponseEntity getNotoundBoardResponse() {
        return ResponseEntity.notFound().build();
    }
}
