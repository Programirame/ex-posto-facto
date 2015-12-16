package com.ex.post.facto.repository;

import com.ex.post.facto.model.Board;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by igstojanovski on 12/13/2015.
 */
public interface BoardRepository extends CrudRepository<Board, Integer> {
}
