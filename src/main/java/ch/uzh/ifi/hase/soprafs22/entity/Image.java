package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Classification;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "IMAGE")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    //work out optional and nullable
    @JsonIgnoreProperties("Images")
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "category", nullable = true)
    private Category category;

    @JsonIgnoreProperties("imagesRated")
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "userId", nullable = true)
    private User owner;

    @JsonIgnoreProperties("imagesRated")
    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "imagesRated")
    private Set<User> ratedBy;

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private Timestamp uploadDate;

    @Column
    private double rating;

    @Column
    private int ratingCounter;

    @Column(unique = true)
    private String storageLink;

    //Change to enum later Maybe
    @Column(unique = false)
    private Classification classification;

    @Column
    private Boolean reachedHighlights;
    //added new
    @Column(nullable = true)
    private Timestamp boostDate;

    //Constructor for testing
    public Image(String name, String location, String storageLink) {
        this.name = name;
        this.location = location;
        this.storageLink = storageLink;
        this.classification = Classification.C;
        this.reachedHighlights = false;
    }

    //No Args Constructor
    public Image() {
        this.classification = Classification.C;
        this.reachedHighlights = false;
        this.rating = 0;
        this.ratingCounter = 0;
    }

    //Getter & Setter
    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void addRatedBy(User user) {
        this.ratedBy.add(user);
    }

    public Set<User> getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(Set<User> ratedBy) {
        this.ratedBy = ratedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRatingCounter() {
        return ratingCounter;
    }

    public void setRatingCounter(int ratingCounter) {
        this.ratingCounter = ratingCounter;
    }

    public String getStorageLink() {
        return storageLink;
    }

    public void setStorageLink(String storageLink) {
        this.storageLink = storageLink;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public Boolean getReachedHighlights() {
        return reachedHighlights;
    }

    public void setReachedHighlights(Boolean reachedHighlights) {
        this.reachedHighlights = reachedHighlights;
    }

    //new get and set for boostDate
    public void setBoostDate(Timestamp boostDate) {
        this.boostDate = boostDate;
    }

    public Timestamp getBoostDate() {
        return boostDate;
    }
}




