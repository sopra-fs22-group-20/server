package ch.uzh.ifi.hase.soprafs22.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void setGetGameId() {
        //setter
        Game game = new Game();
        game.setId(1L);
        //getter
        assertEquals(1L, game.getId());
    }


    @Test
    void setGetUser1Id() {
        Game game = new Game();
        game.setId(1L);
        game.setUser1Id(1L);
        //getter
        assertEquals(1L, game.getUser1Id());
    }

    @Test
    void getUser2Id() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        //getter
        assertEquals(2L, game.getUser2Id());
    }

    @Test
    void setGetUser1Score() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser1Score(0L);
        //getter
        assertEquals(0L, game.getUser1Score());
    }

    @Test
    void setGetUser2Score() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        //getter
        assertEquals(0L, game.getUser2Score());
    }

    @Test
    void setGetUser1Joined() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        //getter
        assertEquals(true, game.getUser1Joined());
    }

    @Test
    void setGetUser2Joined() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        game.setUser2Joined(true);
        //getter
        assertEquals(true, game.getUser2Joined());
    }

    @Test
    void setGetWinner() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        game.setUser2Joined(true);
        game.setWinner(game.getUser2Id());
        //getter
        assertEquals(2L, game.getUser2Id());
    }

    @Test
    void setGetGameStartTime() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        game.setUser2Joined(true);
        game.setWinner(game.getUser1Id());
        Date now = new Date();
        game.setWinner(game.getUser1Id());
        game.setGameStartTime(now);
        //getter
        assertEquals(now, game.getGameStartTime());
    }

    @Test
    void setGetGameCode() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        game.setUser2Joined(true);
        game.setWinner(game.getUser1Id());
        Date now = new Date();
        game.setWinner(game.getUser1Id());
        game.setGameStartTime(now);
        //generate UUID for game
        UUID id = UUID.randomUUID();
        game.setGameCode(id);
        //getter
        assertEquals(id, game.getGameCode());
    }

    @Test
    void setGetActive() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        game.setUser2Joined(true);
        game.setWinner(game.getUser1Id());
        Date now = new Date();
        game.setWinner(game.getUser1Id());
        game.setGameStartTime(now);
        //generate UUID for game
        UUID id = UUID.randomUUID();
        game.setGameCode(id);
        game.setActive(true);
        //getter
        assertEquals(true, game.getActive());
    }

    @Test
    void setGetStep1Image() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        game.setUser2Joined(true);
        game.setWinner(game.getUser1Id());
        Date now = new Date();
        game.setWinner(game.getUser1Id());
        game.setGameStartTime(now);
        //generate UUID for game
        UUID id = UUID.randomUUID();
        game.setGameCode(id);
        game.setActive(true);
        game.setStep1Image("https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/3_jtud40.png");
        //getter
        assertEquals("https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/3_jtud40.png", game.getStep1Image());
    }

    @Test
    void setGetStep2Image() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        game.setUser2Joined(true);
        game.setWinner(game.getUser1Id());
        Date now = new Date();
        game.setWinner(game.getUser1Id());
        game.setGameStartTime(now);
        //generate UUID for game
        UUID id = UUID.randomUUID();
        game.setGameCode(id);
        game.setActive(true);
        game.setStep2Image("https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/3_jtud40.png");
        //getter
        assertEquals("https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/3_jtud40.png", game.getStep2Image());
    }

    @Test
    void setGetStep3Image() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        game.setUser2Joined(true);
        game.setWinner(game.getUser1Id());
        Date now = new Date();
        game.setWinner(game.getUser1Id());
        game.setGameStartTime(now);
        //generate UUID for game
        UUID id = UUID.randomUUID();
        game.setGameCode(id);
        game.setActive(true);
        game.setStep3Image("https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/3_jtud40.png");
        //getter
        assertEquals("https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/3_jtud40.png", game.getStep3Image());
    }

    @Test
    void setGetStep4Image() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        game.setUser2Joined(true);
        game.setWinner(game.getUser1Id());
        Date now = new Date();
        game.setWinner(game.getUser1Id());
        game.setGameStartTime(now);
        //generate UUID for game
        UUID id = UUID.randomUUID();
        game.setGameCode(id);
        game.setActive(true);
        game.setStep4Image("https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/3_jtud40.png");
        //getter
        assertEquals("https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/3_jtud40.png", game.getStep4Image());
    }

    @Test
    void setGetUser1Name() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        game.setUser2Joined(true);
        game.setWinner(game.getUser1Id());
        Date now = new Date();
        game.setWinner(game.getUser1Id());
        game.setGameStartTime(now);
        //generate UUID for game
        UUID id = UUID.randomUUID();
        game.setGameCode(id);
        game.setActive(true);
        game.setUser1Name("jamo");
        game.setStep4Image("https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/3_jtud40.png");
        //getter
        assertEquals("jamo", game.getUser1Name());
    }

    @Test
    void setGetUser2Name() {
        Game game = new Game();
        game.setId(1L);
        game.setUser2Id(2L);
        game.setUser2Score(0L);
        game.setUser1Joined(true);
        game.setUser2Joined(true);
        game.setWinner(game.getUser1Id());
        Date now = new Date();
        game.setWinner(game.getUser1Id());
        game.setGameStartTime(now);
        //generate UUID for game
        UUID id = UUID.randomUUID();
        game.setGameCode(id);
        game.setActive(true);
        game.setUser1Name("jamo");
        game.setUser2Name("Omar");
        game.setStep4Image("https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/3_jtud40.png");
        //getter
        assertEquals("Omar", game.getUser2Name());
    }
}