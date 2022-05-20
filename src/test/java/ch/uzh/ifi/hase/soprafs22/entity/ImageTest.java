package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Classification;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ImageTest {


    @Test
    void setGetImageId() {
        //setter
        Image image = new Image();
        image.setImageId(1l);

        //getter
        assertEquals(1l, image.getImageId());
    }


    @Test
    void setGetName() {
        //setter
        Image image = new Image();
        image.setName("name");

        //getter
        assertEquals("name", image.getName());
    }


    @Test
    void setGetLocation() {
        //setter
        Image image = new Image();
        image.setLocation("location");

        //getter
        assertEquals("location", image.getLocation());
    }


    @Test
    void setGetUploadDate() {
        //setter
        Image image = new Image();
        Timestamp date = new Timestamp(System.currentTimeMillis());
        image.setUploadDate(date);

        //getter
        assertEquals(date, image.getUploadDate());
    }


    @Test
    void setGetRating() {
        //setter
        Image image = new Image();
        image.setRating(0);

        //getter
        assertEquals(0, image.getRating());
    }


    @Test
    void setGetStorageLink() {
        //setter
        Image image = new Image();
        image.setStorageLink("storageLink");

        //getter
        assertEquals("storageLink", image.getStorageLink());
    }


    @Test
    void setGetClassification() {
        //setter
        Image image = new Image();
        image.setClassification(Classification.A);

        //getter
        assertEquals("A", image.getClassification());
    }


    @Test
    void setGetReachedHighlights() {
        //setter
        Image image = new Image();
        image.setReachedHighlights(false);

        //getter
        assertEquals(false, image.getReachedHighlights());
    }



}