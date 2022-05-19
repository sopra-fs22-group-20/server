package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserGetDTOTest {
    @Test
    void setGetImageGetDTO() {
        // create test Date
        Timestamp date = new Timestamp(System.currentTimeMillis());
        // create test User
        User testUser = new User();

        // create image
        Image image = new Image();
        image.setOwner(testUser);
        image.setName("name");
        image.setLocation("location");
        image.setUploadDate(date);
        image.setRating(0);
        image.setStorageLink("storageLink");
        image.setClassification("C");
        image.setReachedHighlights(false);

        // create UserPostDTO
        ImageGetDTO imageGetDTO = new ImageGetDTO();
        imageGetDTO.setOwner(testUser);
        imageGetDTO.setName("name");
        imageGetDTO.setLocation("location");
        imageGetDTO.setUploadDate(date);
        imageGetDTO.setRating(0);
        imageGetDTO.setStorageLink("storageLink");
        imageGetDTO.setClassification("C");
        imageGetDTO.setReachedHighlights(false);

        // create image Entity via mapper
        ImageGetDTO createdImage = DTOMapper.INSTANCE.convertEntityToImageGetDTO(image);

        // check getters

        assertEquals(testUser, createdImage.getOwner());
        assertEquals(image.getName(), createdImage.getName());
        assertEquals(image.getLocation(), createdImage.getLocation());
        assertEquals(date, createdImage.getUploadDate());
        assertEquals(image.getRating(), createdImage.getRating());
        assertEquals(image.getStorageLink(), createdImage.getStorageLink());
        assertEquals(image.getClassification(), createdImage.getClassification());
        assertEquals(image.getReachedHighlights(), createdImage.getReachedHighlights());
    }

}