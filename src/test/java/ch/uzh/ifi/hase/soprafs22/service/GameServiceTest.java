package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.rest.dto.GameDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

@SpringBootTest
class GameServiceTest {

    private Game game;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setActive(true);
        game.setUser1Id(1L);
        game.setUser1Score(1L);
        game.setUser1Joined(true);
    }

    @Test
    void createGame() {
        try{
            Game response = gameService.createGame(game);
            assertEquals("Game is being created by user 1 (Joined User 1)", game.getUser1Joined(), response.getUser1Joined());
            assertNotEquals("Game Code is <> aaa1-akjshd-a213223,283838", "aaa1-akjshd-a213223,283838", response.getGameCode());
            assertEquals("Game is active Now: ", TRUE, response.getActive());
            assertNotEquals("Player 2 has not joined yet: ", TRUE, response.getUser2Joined());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void joinGame() {
        try{
            Game response = gameService.createGame(game);
            response.setUser2Id(2L);
            response.setUser2Joined(TRUE);
            response.setUser2Score(0L);
            Game joinedGameResponse = gameService.joinGame(response);
            assertEquals("Game is joined by both users: ", TRUE, joinedGameResponse.getUser2Joined());
            assertEquals("Game is active for both users: ", TRUE, joinedGameResponse.getActive());
            assertNotEquals("User2 Joined Status is not null: ", FALSE, joinedGameResponse.getUser2Joined());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void quit() {
        try{
            Game response = gameService.createGame(game);
            response.setUser2Id(2L);
            response.setUser2Joined(TRUE);
            response.setUser2Score(0L);
            Game joinedGameResponse = gameService.joinGame(response);
            //now game is created by both of the users 1 and two, second user will quit the game by himself
            Game quitGame = gameService.quit(joinedGameResponse.getGameCode(), joinedGameResponse.getUser2Id());
            //as user2 has quited the game first, now user1 should be the winner
            assertEquals("User1 should be the winner: ", quitGame.getUser1Id(), quitGame.getWinner());
            assertNotEquals("User2 is looser", quitGame.getUser2Id(), quitGame.getWinner());
            assertEquals("Game has been deactivated now", FALSE, quitGame.getActive());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findGameByGameCode() {
        try{
            Game response = gameService.createGame(game);
            response.setUser2Id(2L);
            response.setUser2Joined(TRUE);
            response.setUser2Score(0L);
            Game joinedGame = gameService.joinGame(response);
            assertEquals("Game code of creator and joining party are same", joinedGame.getGameCode(), response.getGameCode());
            assertEquals("Game is found by game code ", joinedGame.getGameCode(), gameService.findGameByGameCode(joinedGame.getGameCode()).getGameCode());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void updateScore() {
        try{
            Game response = gameService.createGame(game);
            response.setUser2Id(2L);
            response.setUser2Joined(TRUE);
            response.setUser2Score(0L);
            Game joinedGame = gameService.joinGame(response);
            //TILL now both of the users have joined the game, now we will update the score of user1 and test is it giving us the same score  or not?
            gameService.updateScore(new GameDTO(1L, joinedGame.getGameCode(), 1L, 0L));
            assertEquals("Updated Score of user1 is 1", Double.parseDouble("1L"), gameService.findActiveGame().getUser1Score());
            assertEquals("Game is found by game code ", joinedGame.getGameCode(), gameService.findGameByGameCode(joinedGame.getGameCode()).getGameCode());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findAllGames() {
        try{
            Game response = gameService.createGame(game);
            response.setUser2Id(2L);
            response.setUser2Joined(TRUE);
            response.setUser2Score(0L);
            gameService.joinGame(response);
            //game is created now
            assertNotEquals("Game found > 0", 0, gameService.findAllGames().size());
            assertEquals("More than one games found as all (ACTIVE FALSE, TRUE)", 8L, gameService.findAllGames().size());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findActiveGame() {
        try{
            Game response = gameService.createGame(game);
            response.setUser2Id(2L);
            response.setUser2Joined(TRUE);
            response.setUser2Score(0L);
            gameService.joinGame(response);
            //game is created now
            assertEquals("We found one active game", TRUE, gameService.findActiveGame().getActive());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findGameByUserId() {
        try{
            Game response = gameService.createGame(game);
            response.setUser2Id(2L);
            response.setUser2Joined(TRUE);
            response.setUser2Score(0L);
            gameService.joinGame(response);
            //game is created now
            assertEquals("We Found game for user having id = 1", "1L", gameService.findGameByUserId(1L).getUser1Id());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}