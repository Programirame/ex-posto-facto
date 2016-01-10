package com.ex.posto.facto;

import com.ex.post.facto.Application;
import com.ex.post.facto.model.Board;
import com.ex.post.facto.repository.BoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * End to end Integration test for the Board Controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest("server.port:4488")
@SpringApplicationConfiguration(classes = Application.class)
public class BoardControllerEndToEndIT {

    @Autowired
    BoardRepository boardRepository;

    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void createNewBoardTest() {
        String newBoardJson = "{\n" +
                "    \"id\" : -1,\n" +
                "    \"boardName\":\"HATEOAS Board\",\n" +
                "    \"boardDescription\":\"First Time HATEOAS Board\"\n" +
                "    \n" +
                "}";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(newBoardJson, requestHeaders);

        HttpStatus status = restTemplate.exchange("http://localhost:4488/board", HttpMethod.POST, requestEntity,
                String.class).getStatusCode();

        assertEquals(status, HttpStatus.OK);
    }

    @Test
    public void getExistingBoardTest() {
        Board testBoard = new Board();
        testBoard.setBaordId(12);
        testBoard.setBoardDescription("Epten Test");
        testBoard.setBoardName("Test Board");

        int i = boardRepository.save(testBoard).getBoardId();

        HashMap<String, String> variables = new HashMap<>();
        variables.put("id", String.valueOf(i));

        Board board = restTemplate.getForObject("http://localhost:4488/board/"+i, Board.class, variables);

        assertTrue(board.getBoardId() == i);
    }
}
