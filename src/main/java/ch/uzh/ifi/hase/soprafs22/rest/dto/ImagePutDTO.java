package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.Classification;

public class ImagePutDTO {

    private Long imageId;
    private String name;
    private double rating;
    private Classification classification;



    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
/**
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
/**
    public Set<User> getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(Set<User> ratedBy) {
        this.ratedBy = ratedBy;
    }
*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/**
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
*/
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
/**
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
*/
    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }
/**
    public Boolean getReachedHighlights() {
        return reachedHighlights;
    }

    public void setReachedHighlights(Boolean reachedHighlights) {
        this.reachedHighlights = reachedHighlights;
    }

    public Timestamp getBoostDate() {
        return boostDate;
    }

    public void setBoostDate(Timestamp boostDate) {
        this.boostDate = boostDate;
    }
    */
}
