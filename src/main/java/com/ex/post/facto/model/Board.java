package com.ex.post.facto.model;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Representation of the Retrospective Board. Important here is only the name and the ID.
 * Each post to the board is linked to this board ID in an other entity.
 */
@Entity
public class Board extends ResourceSupport {

    @Id
    @GeneratedValue
    @Column(name="BOARD_ID")
    public Integer baordId;

    @Column(name="NAME")
    public String boardName;

    @Column(name="DESCRIPTION")
    public String boardDescription;

    public Integer getBoardId() {
        return baordId;
    }

    public void setBaordId(Integer baordId) {
        this.baordId = baordId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardDescription() {
        return boardDescription;
    }

    public void setBoardDescription(String boardDescription) {
        this.boardDescription = boardDescription;
    }
}
