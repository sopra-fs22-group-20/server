package ch.uzh.ifi.hase.soprafs22.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes
 * the primary key
 */
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

    @JsonBackReference
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;

    @JsonIgnoreProperties("owner")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "images_seen_by",
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
    private Date creationDate;

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

    public int getHighlightCounter() {
        return highlightCounter;
    }

    public void setHighlightCounter(int highlightCounter) {
        this.highlightCounter = highlightCounter;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
