package com.ex.posto.facto;


import com.ex.post.facto.controller.BoardController;
import com.ex.post.facto.model.Board;
import com.ex.post.facto.service.BoardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ControllerContextConfiguration.class, initializers = ConfigFileApplicationContextInitializer.class)
@WebIntegrationTest
public class BoardControllerIT {

    private MockMvc mockMvc;

    @Mock
    private BoardService boardService;

    @InjectMocks
    private BoardController boardController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        Board dummyBoard = getDummyBoard();

        when(boardService.findOne(1)).thenReturn(dummyBoard);
        when(boardService.saveBoard(dummyBoard)).thenReturn(dummyBoard);

        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    public void simpleQueryTest() throws Exception {
        String newBoardJson = "{\n" +
                "    \"id\" : 1,\n" +
                "    \"boardName\":\"HATEOAS Board\",\n" +
                "    \"boardDescription\":\"First Time HATEOAS Board\"\n" +
                "    \n" +
                "}";

        this.mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON).content(newBoardJson))
                .andExpect(status().isOk());
    }

    private Board getDummyBoard() {
        Board board = new Board();

        board.setBaordId(1);
        board.setBoardName("HATEOAS Board");
        board.setBoardDescription("First Time HATEOAS Board");

        return board;
    }

}
