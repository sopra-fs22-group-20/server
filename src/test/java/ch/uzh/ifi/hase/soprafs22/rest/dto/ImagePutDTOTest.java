package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ImagePutDTOTest {
    @Test
void setGetImagePutDTO() {
    // create test Date
    Timestamp date = new Timestamp(System.currentTimeMillis());
    // create test User
    User testUser = new User();

    // create UserPostDTO
    ImagePutDTO imagePutDTO = new ImagePutDTO();
        imagePutDTO.setOwner(testUser);
        imagePutDTO.setName("name");
        imagePutDTO.setLocation("location");
        imagePutDTO.setUploadDate(date);
        imagePutDTO.setRating(0);
        imagePutDTO.setStorageLink("storageLink");
        imagePutDTO.setClassification("C");
        imagePutDTO.setReachedHighlights(false);

    // create image Entity via mapper
    Image image = DTOMapper.INSTANCE.convertImagePutDTOtoEntity(imagePutDTO);

    // check getters
    assertEquals(testUser, imagePutDTO.getOwner());
    assertEquals(image.getName(), imagePutDTO.getName());
    assertEquals(image.getLocation(), imagePutDTO.getLocation());
    assertEquals(date, imagePutDTO.getUploadDate());
    assertEquals(image.getRating(), imagePutDTO.getRating());
    assertEquals(image.getStorageLink(), imagePutDTO.getStorageLink());
    assertEquals(image.getClassification(), imagePutDTO.getClassification());
    assertEquals(image.getReachedHighlights(), imagePutDTO.getReachedHighlights());

}

}