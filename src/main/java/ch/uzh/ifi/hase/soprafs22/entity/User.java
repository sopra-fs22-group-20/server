package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

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
    @GeneratedValue
    private Long user_id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    /** After Images is done
    @Column(unique = true)
    private ArrayList<Long> images;

     @Column(unique = true)
     private ArrayList<Long> image_seen;
     **/

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String more_info;

    //Constructor
    public User(String username, String password, String email, String more_info) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.more_info = more_info;
    }

    //No Args Constructor
    public User() {
    }

    //Getter & Setter
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMore_info() {
        return more_info;
    }

    public void setMore_info(String more_info) {
        this.more_info = more_info;
    }

    /**
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

     public String getImage_seen() {
     return image_seen;
     }

     public void setImage_seen(String image_seen) {
     this.image_seen = image_seen;
     }
     **/
}
