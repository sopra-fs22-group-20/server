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
    private Long image_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long category;

    @Column(nullable = false)
    private Long owner;

    @Column(nullable = false)
    private Long location;

    @Column(nullable = false)
    private Date upload_date;

    @Column(nullable = false)
    private int rating;

    public Image(String name, Long category, Long owner, Long location, Date upload_date, int rating) {
        this.name = name;
        this.category = category;
        this.owner = owner;
        this.location = location;
        this.upload_date = upload_date;
        this.rating = rating;
    }

    public Image() {
    }

    public Long getImage_id() {
        return image_id;
    }

    public String getName() {
        return name;
    }

    public Long getCategory() {
        return category;
    }

    public Long getOwner() {
        return owner;
    }

    public Long getLocation() {
        return location;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}