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
     * <p>
     * After User and dependency is done
     * @Column(nullable = false)
     * private User owner;
     */

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Date upload_date;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private String storageLink;

    //Change to enum later Maybe
    @Column(nullable = false)
    private String classification;

    @Column(nullable = false)
    private Boolean reachedHighlights;


    public Image(String name, String location, String storageLink) {
        this.name = name;
        this.location = location;
        this.storageLink = storageLink;
        this.classification = "C";
        this.reachedHighlights = false;
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

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
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
}




