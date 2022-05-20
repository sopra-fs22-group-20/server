package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.Category;

public class ImagePostDTO {

    private Category category;
    private String name;
    private String location;
    private String storageLink;


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public String getStorageLink() {
        return storageLink;
    }

    public void setStorageLink(String storageLink) {
        this.storageLink = storageLink;
    }
}
