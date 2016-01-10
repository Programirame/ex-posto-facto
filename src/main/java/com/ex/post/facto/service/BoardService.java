package com.ex.post.facto.service;

import com.ex.post.facto.model.Board;
import com.ex.post.facto.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepo;

    public Board saveBoard(Board board) {
        return boardRepo.save(board);
    }

    public Board findOne(Integer id) {
        return boardRepo.findOne(id);
    }
}
