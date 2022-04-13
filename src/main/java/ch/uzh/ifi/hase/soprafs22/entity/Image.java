package ch.uzh.ifi.hase.soprafs22.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    @GeneratedValue
    private Long imageID;

    /**
     * After Category is done
     *
     * @Column(nullable = false)
     * private Category categoryID;
     */

    //Check remove "save the orphants"
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "userId", nullable = true)
    private User owner;

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private Date uploadDate;

    @Column
    private int rating;

    @Column(unique = true)
    private String storageLink;

    //Change to enum later Maybe
    @Column(unique = false)
    private String classification;

    @Column
    private Boolean reachedHighlights;


    public Image(String name, String location, String storageLink, User owner) {
        this.name = name;
        this.location = location;
        this.storageLink = storageLink;
        this.classification = "C";
        this.reachedHighlights = false;
        this.owner = owner;
    }

    public Image() {
    }

    public Long getImageID() {
        return imageID;
    }

    public void setImageID(Long imageID) {
        this.imageID = imageID;
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

    public void setUploadDate(Date upload_date) {
        this.uploadDate = upload_date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}




