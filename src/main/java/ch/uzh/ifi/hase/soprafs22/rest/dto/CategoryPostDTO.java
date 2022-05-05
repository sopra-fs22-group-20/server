package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.Image;

import java.util.Set;

public class CategoryPostDTO {

    private String category;
    //private Set<Image> images;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
/**
 public Set<Image> getImages() {
 return images;
 }

 public void setImages(Set<Image> images) {
 this.images = images;
 }
 */
}
