package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImagePostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;


import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ImagePostDTOTest {



    @Test
    void setGetImagePostDTO() {
        // create test Date
        Timestamp date = new Timestamp(System.currentTimeMillis());
        // create test User
        User testUser = new User();

        // create UserPostDTO
        ImagePostDTO imagePostDTO = new ImagePostDTO();
        imagePostDTO.setOwner(testUser);
        imagePostDTO.setName("name");
        imagePostDTO.setLocation("location");
        imagePostDTO.setUploadDate(date);
        imagePostDTO.setRating(0);
        imagePostDTO.setStorageLink("storageLink");
        imagePostDTO.setClassification("C");
        imagePostDTO.setReachedHighlights(false);

        // create image Entity via mapper
        Image image =DTOMapper.INSTANCE.convertImagePostDTOtoEntity(imagePostDTO);

        // check getters
        assertEquals(testUser, imagePostDTO.getOwner());
        assertEquals(image.getName(), imagePostDTO.getName());
        assertEquals(image.getLocation(), imagePostDTO.getLocation());
        assertEquals(date, imagePostDTO.getUploadDate());
        assertEquals(image.getRating(), imagePostDTO.getRating());
        assertEquals(image.getStorageLink(), imagePostDTO.getStorageLink());
        assertEquals(image.getClassification(), imagePostDTO.getClassification());
        assertEquals(image.getReachedHighlights(), imagePostDTO.getReachedHighlights());

    }

}