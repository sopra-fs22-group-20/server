package ch.uzh.ifi.hase.soprafs22.rest.dto;


import java.util.UUID;

public class GameDTO {
    private Long userId; //who is updating the score his id will be here
    private UUID gameCode; // perticular iteration o fgame
    private Long score1;//user 1 score
    private Long score2;//user 2 score

    public GameDTO() {
    }

    public GameDTO(Long userId, UUID gameCode, Long score1, Long score2) {
        this.userId = userId;
        this.gameCode = gameCode;
        this.score1 = score1;
        this.score2 = score2;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UUID getGameCode() {
        return gameCode;
    }

    public void setGameCode(UUID gameCode) {
        this.gameCode = gameCode;
    }

    public Long getScore1() {
        return score1;
    }

    public void setScore1(Long score1) {
        this.score1 = score1;
    }

    public Long getScore2() {
        return score2;
    }

    public void setScore2(Long score2) {
        this.score2 = score2;
    }
}
