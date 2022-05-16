package ch.uzh.ifi.hase.soprafs22.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "GAME")
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long user1Id;
    private Long user2Id;
    private Long user1Score;
    private Long user2Score;
    private Boolean user1Joined;
    private Boolean user2Joined;
    private Long winner;
    private Date gameStartTime;
    private UUID gameCode;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Long user1Id) {
        this.user1Id = user1Id;
    }

    public Long getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Long user2Id) {
        this.user2Id = user2Id;
    }

    public Long getUser1Score() {
        return user1Score;
    }

    public void setUser1Score(Long user1Score) {
        this.user1Score = user1Score;
    }

    public Long getUser2Score() {
        return user2Score;
    }

    public void setUser2Score(Long user2Score) {
        this.user2Score = user2Score;
    }

    public Boolean getUser1Joined() {
        return user1Joined;
    }

    public void setUser1Joined(Boolean user1Joined) {
        this.user1Joined = user1Joined;
    }

    public Boolean getUser2Joined() {
        return user2Joined;
    }

    public void setUser2Joined(Boolean user2Joined) {
        this.user2Joined = user2Joined;
    }

    public Long getWinner() {
        return winner;
    }

    public void setWinner(Long winner) {
        this.winner = winner;
    }

    public Date getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(Date gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public UUID getGameCode() {
        return gameCode;
    }

    public void setGameCode(UUID gameCode) {
        this.gameCode = gameCode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
