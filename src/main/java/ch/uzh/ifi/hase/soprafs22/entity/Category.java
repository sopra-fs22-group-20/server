package ch.uzh.ifi.hase.soprafs22.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;


    //Constructor for testing
    public Category(String name) {
        this.name = name;
    }

    //No-Args Constructor
    public Category() {
    }

    //Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String category) {
        this.name = category;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", images=" + images +
                '}';
    }
}