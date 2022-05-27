package ch.uzh.ifi.hase.soprafs22.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "GAME")
public class Game {
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
    @Transient
    private String user1Name;
    @Transient
    private String user2Name;
    private UUID gameCode;
    private Boolean active;
    // TODO: 22/05/2022 Change cloudnary part of images
    private String step1Image = "https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/3_jtud40.png";
    private String step2Image = "https://res.cloudinary.com/doledaump/image/upload/v1653211620/SoPRa/2_ruj1bq.png";
    private String step3Image = "https://res.cloudinary.com/doledaump/image/upload/v1653211621/SoPRa/1_uj7dsk.png";
    private String step4Image = "https://res.cloudinary.com/doledaump/image/upload/v1653211624/SoPRa/100_nfo6px.gif";



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

    public String getStep1Image() {
        return step1Image;
    }

    public String getUser1Name() {
        return user1Name;
    }

    public void setUser1Name(String user1Name) {
        this.user1Name = user1Name;
    }

    public String getUser2Name() {
        return user2Name;
    }

    public void setUser2Name(String user2Name) {
        this.user2Name = user2Name;
    }

    public String getStep2Image() {
        return step2Image;
    }

    public void setStep2Image(String step2Image) {
        this.step2Image = step2Image;
    }

    public String getStep3Image() {
        return step3Image;
    }

    public void setStep3Image(String step3Image) {
        this.step3Image = step3Image;
    }

    public String getStep4Image() {
        return step4Image;
    }

    public void setStep4Image(String step4Image) {
        this.step4Image = step4Image;
    }

    public void setStep1Image(String step1Image) {
        this.step1Image = step1Image;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
