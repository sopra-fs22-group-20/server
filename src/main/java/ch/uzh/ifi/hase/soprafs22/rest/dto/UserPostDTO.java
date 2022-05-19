package ch.uzh.ifi.hase.soprafs22.rest.dto;

import java.sql.Timestamp;

public class UserPostDTO {

    private Long userId;
    private String token;
    private String username;
    private String password;
    //private Set<Image> images;
    //private Set<Image> imagesRated;
    private String email;
    private String moreInfo;
    private int trophies;
    private String highlightCounter;
    private Timestamp creationDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
/**
    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
/**
    public Set<Image> getImagesRated() {
        return imagesRated;
    }

    public void setImagesRated(Set<Image> imagesRated) {
        this.imagesRated = imagesRated;
    }
*/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public String getHighlightCounter() {
        return highlightCounter;
    }

    public void setHighlightCounter(String highlightCounter) {
        this.highlightCounter = highlightCounter;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
