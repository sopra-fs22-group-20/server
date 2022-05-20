package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.Classification;
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
        image.setClassification(Classification.C);
        image.setReachedHighlights(false);

        // create UserPostDTO
        ImageGetDTO imageGetDTO = new ImageGetDTO();
        imageGetDTO.setName("name");
        imageGetDTO.setRating(0);

        // create image Entity via mapper
        ImageGetDTO createdImage = DTOMapper.INSTANCE.convertEntityToImageGetDTO(image);

        // check getters

        assertEquals(image.getName(), createdImage.getName());
        assertEquals(image.getRating(), createdImage.getRating());
    }

}