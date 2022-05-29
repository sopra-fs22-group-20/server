package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.service.CategoryService;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    void createGame() throws Exception {
        //given
        Game testGame = new Game();
        String mockGameJson = "{\"user1Id\":1,\"user1Joined\":true}";
        UUID gameCode = UUID.randomUUID();
        testGame.setGameCode(gameCode);
        testGame.setId(1L);
        testGame.setUser1Id(1L);
        testGame.setUser1Joined(true);
        //Mock
        given(gameService.createGame(testGame)).willReturn(testGame);
        //when
        MockHttpServletRequestBuilder postRequest = post("/game/create").contentType(MediaType.APPLICATION_JSON).content(mockGameJson);
        //then
        mockMvc.perform(postRequest).andExpect(status().isOk());
    }

    @Test
    void findAllGames() throws Exception {
        //given
        Game testGame = new Game();
        UUID gameCode = UUID.randomUUID();
        testGame.setGameCode(gameCode);
        testGame.setId(1L);

        List<Game> allGames = Collections.singletonList(testGame);

        //Mock
        given(gameService.findAllGames()).willReturn(allGames);

        //when
        MockHttpServletRequestBuilder getRequest = get("/game/findAllGames").contentType(MediaType.APPLICATION_JSON);

        //then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].gameCode", is("" + testGame.getGameCode() + "")))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void findActiveGamme() throws Exception {
        //given
        Game testGame = new Game();
        UUID gameCode = UUID.randomUUID();
        testGame.setGameCode(gameCode);
        testGame.setId(1L);
        testGame.setActive(true);

        //Mock
        given(gameService.findActiveGame()).willReturn(testGame);

        //when
        MockHttpServletRequestBuilder getRequest = get("/game/findActiveGame").contentType(MediaType.APPLICATION_JSON);

        //then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.gameCode", is("" + testGame.getGameCode() + "")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void findGameByUserId() throws Exception {
        //given
        Game testGame = new Game();
        UUID gameCode = UUID.randomUUID();
        testGame.setGameCode(gameCode);
        testGame.setId(1L);
        testGame.setUser1Id(1L);
        testGame.setActive(true);

        //Mock
        given(gameService.findGameByUserId(testGame.getUser1Id())).willReturn(testGame);

        //when
        MockHttpServletRequestBuilder getRequest = get("/game/findGameByUserId/" + testGame.getUser1Id()).contentType(MediaType.APPLICATION_JSON);

        //then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.gameCode", is("" + testGame.getGameCode() + "")))
                .andExpect(jsonPath("$.user1Id", is(1)))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void checkForWinner() throws Exception {
        //given
        Game testGame = new Game();
        UUID gameCode = UUID.randomUUID();
        testGame.setGameCode(gameCode);
        testGame.setId(1L);
        testGame.setUser1Id(1L);
        testGame.setWinner(testGame.getUser1Id());
        testGame.setActive(true);

        //Mock
        given(gameService.checkForWinner()).willReturn(testGame);

        //when
        MockHttpServletRequestBuilder getRequest = get("/game/checkForWinner").contentType(MediaType.APPLICATION_JSON);

        //then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.gameCode", is("" + testGame.getGameCode() + "")))
                .andExpect(jsonPath("$.user1Id", is(1)))
                .andExpect(jsonPath("$.winner", is(1)))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void saveWinner() throws Exception {
        //given
        Game testGame = new Game();
        UUID gameCode = UUID.randomUUID();
        testGame.setGameCode(gameCode);
        testGame.setId(1L);
        testGame.setUser1Id(1L);
        testGame.setWinner(testGame.getUser1Id());
        testGame.setActive(true);

        //Mock
        given(gameService.saveWinner(testGame.getId(), gameCode)).willReturn(testGame);

        //when
        MockHttpServletRequestBuilder getRequest = get("/game/saveWinner/" + testGame.getId() + "/" + testGame.getGameCode()).contentType(MediaType.APPLICATION_JSON);

        //then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.gameCode", is("" + testGame.getGameCode() + "")))
                .andExpect(jsonPath("$.user1Id", is(1)))
                .andExpect(jsonPath("$.winner", is(1)))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void checkEmptyLobby() throws Exception {
        //when
        MockHttpServletRequestBuilder getRequest = get("/game/checkEmptyLobby").contentType(MediaType.APPLICATION_JSON);

        //then
        //lobby is empty for now
        mockMvc.perform(getRequest).andExpect(status().isAlreadyReported());
    }
}