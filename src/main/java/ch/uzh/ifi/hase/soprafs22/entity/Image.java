package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Classification;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Internal Image Representation
 * This class composes the internal representation of the image and defines how
 * the image is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes
 * the primary key
 */

@Entity
@Table(name = "IMAGE")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    /**
     * After Category is done
     *
     * @Column(nullable = false)
     * private Category categoryID;
     */

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

    /**
    @Column
    private Classification classification; */

    @Column
    private String location;

    @Column
    private Date uploadDate;

    @Column
    private double rating;

    @Column
    private int ratingCounter;

    @Column(unique = true)
    private String storageLink;

    //Change to enum later Maybe
    @Column(unique = false)
    private String classification;

    @Column
    private Boolean reachedHighlights;

    //Constructor for testing
    public Image(String name, String location, String storageLink) {
        this.name = name;
        this.location = location;
        this.storageLink = storageLink;
        this.classification = "C";
        this.reachedHighlights = false;
    }

    //No Args Constructor
    public Image() {
        this.classification = "C";
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

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
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

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Boolean getReachedHighlights() {
        return reachedHighlights;
    }

    public void setReachedHighlights(Boolean reachedHighlights) {
        this.reachedHighlights = reachedHighlights;
    }
}




