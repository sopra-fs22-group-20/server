package ch.uzh.ifi.hase.soprafs22.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int trophies = 0;

    @JsonBackReference
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;

    @JsonIgnoreProperties("owner")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "images_rated_by",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "imageId"))
    private Set<Image> imagesRated;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String moreInfo;

    @Column(unique = false)
    private int highlightCounter;

    @Column(unique = false)
    private Timestamp creationDate;

    //Constructor for testing
    public User(String username, String password, String email, String moreInfo) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.moreInfo = moreInfo;
        this.highlightCounter = 0;
    }

    //No Args Constructor
    public User() {
        this.highlightCounter = 0;
    }

    //Getter & Setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    //Getter & Setter
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

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<Image> getImagesRated() {
        return imagesRated;
    }

    public void setImagesRated(Set<Image> imagesRated) {
        this.imagesRated = imagesRated;
    }

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

    public void setTrophies(int trophy) {
        this.trophies = this.trophies + trophy;
    }

    public int getHighlightCounter() {
        return highlightCounter;
    }

    public void setHighlightCounter(int highlightCounter) {
        this.highlightCounter = highlightCounter;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
