package com.ex.posto.facto;


import com.ex.post.facto.controller.BoardController;
import com.ex.post.facto.model.Board;
import com.ex.post.facto.service.BoardService;
import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        Board dummyBoard = getDummyBoard(1);

        when(boardService.findOne(1)).thenReturn(dummyBoard);
        when(boardService.saveBoard(dummyBoard)).thenReturn(dummyBoard);
        when(boardService.findAll()).thenReturn(getThreeBoards());
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    public void createNewBoardTest() throws Exception {
        String newBoardJson = "{\n" +
                "    \"id\" : null,\n" +
                "    \"boardName\":\"HATEOAS Board\",\n" +
                "    \"boardDescription\":\"First Time HATEOAS Board\"\n" +
                "    \n" +
                "}";

        MvcResult result = this.mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON).content(newBoardJson))
                .andExpect(status().isOk()).andReturn();

        String location = result.getResponse().getHeader("Location");

        verify(boardService).saveBoard(getDummyBoard(1));
        assertTrue(location.endsWith("/board/1"));
    }

    @Test
    public void retrieveExistingBoardById() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/board/1"))
                .andExpect(status().isOk()).andReturn();

        verify(boardService).findOne(1);

        String json = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(json);
        JSONArray links = obj.getJSONArray("links");

        assertTrue(links.length() > 0);
    }

    @Test
    public void retrieveNonExistingBoardById() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/board/2"))
                .andExpect(status().isNotFound()).andReturn();

        verify(boardService).findOne(2);

        String json = result.getResponse().getContentAsString();
        assertTrue(json.isEmpty());
    }

    @Test
    public void retrieveAllBoards() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/board"))
                .andExpect(status().isOk()).andReturn();

        verify(boardService).findAll();

        String json = result.getResponse().getContentAsString();
        assertTrue(json.length()>0);

        JSONArray obj = new JSONArray(json);
        assertTrue(obj.length() == 3);

    }



    private Board getDummyBoard(int id) {
        Board board = new Board();

        board.setBaordId(id);
        board.setBoardName("HATEOAS Board");
        board.setBoardDescription("First Time HATEOAS Board");

        return board;
    }

    private Iterable<Board> getThreeBoards() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(getDummyBoard(1));
        boards.add(getDummyBoard(2));
        boards.add(getDummyBoard(3));

        return boards;
    }

}
