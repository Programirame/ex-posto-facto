package com.ex.post.facto.controller;

import com.ex.post.facto.model.Board;
import com.ex.post.facto.repository.BoardRepository;
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
public class RetrospectiveController {

    @Autowired
    BoardRepository boardRepo;

    /**
     * Creates a new board. Returns the URL of the newly created resource in the response header.
     * @param board
     * @return
     */
    @RequestMapping(value = "/board", method = RequestMethod.POST)
    public ResponseEntity<?> createRetrospective(@RequestBody Board board) {

        Board newBoard = boardRepo.save(board);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newBoard.getBoardId()).toUri();
        responseHeaders.setLocation(newPollUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);
    }

    /**
     * Retrieves a board for the id sent as a path variable.
     * @param id
     * @return
     */
    @RequestMapping(value = "/board/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getRetrospectiveBoard(@PathVariable Integer id) {

        Board board = boardRepo.findOne(id);
        ResponseEntity<?> response;
        if(board != null) {
            board.add(linkTo(methodOn(RetrospectiveController.class).getRetrospectiveBoard(id)).withSelfRel());
            response = new ResponseEntity<>(boardRepo.findOne(id), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }


}
