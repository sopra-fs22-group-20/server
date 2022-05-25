package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.Classification;
import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.entity.Image;

import ch.uzh.ifi.hase.soprafs22.repository.CategoryRepository;
import ch.uzh.ifi.hase.soprafs22.repository.ImageRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImagePutDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ImageServiceTest {
    @Mock
    private ImageRepository imageRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ImageService imageService;

    private Image testImage;

    private Category testcategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        testcategory = new Category();
        testcategory.setName("Fish");

        // given
        testImage = new Image();
        testImage.setImageId(1l);
        testImage.setName("image");
        testImage.setLocation("location");
        testImage.setStorageLink("storage");
        testImage.setClassification(Classification.C);
        testImage.setCategory(testcategory);


        // when -> any object is being save in the userRepository -> return the dummy
        // testImage
        Mockito.when(imageRepository.save(Mockito.any())).thenReturn(testImage);
    }

    @Test
    void createImage() {
        // testUser
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        // when -> any object is being save in the userRepository -> return the dummy
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testcategory);

        Image createdImage = imageService.createImage(testImage, testUser);

        // then
        Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

        //assertions
        assertEquals(testImage.getImageId(), createdImage.getImageId());
        assertEquals(testImage.getName(), createdImage.getName());

    }

    @Test
    void getAllImagesOfUser() {
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        //Setup of the image and category
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testcategory);
        imageService.createImage(testImage, testUser);
        Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

        // create the image and get all the images
        // when -> any object is being save in the userRepository -> return the dummy
        List<Image> allImages = Collections.singletonList(testImage);

        given(imageRepository.findImagesByOwnerUserId(Mockito.anyLong())).willReturn((allImages));
        List<Image> image = imageService.getAllImagesOfUser(1L);

        //assertions
        assertEquals(image.get(0).getImageId(), testImage.getImageId());
    }

    @Test
    void getImageByImageId() {
        // testUser
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        // when -> any object is being save in the userRepository -> return the dummy
        //Setup of the image and category
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testcategory);
        imageService.createImage(testImage, testUser);
        Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

        given(imageRepository.findImageByImageId(Mockito.anyLong())).willReturn(testImage);
        // then
        Image image = imageService.getImageByImageId(1L);

        //assertions
        assertEquals(testImage.getImageId(), image.getImageId());
    }

    @Test
    void getRandomNonRatedImageFromCategory_non_rated_exist() {
        // testUser
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        // when -> any object is being save in the userRepository -> return the dummy
        //Setup of the image and category
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testcategory);
        imageService.createImage(testImage, testUser);
        Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

        given(imageRepository.findRandomImageFromCategory(Mockito.anyString(), Mockito.anyInt())).willReturn(testImage);
        given(imageRepository.ratingCheck(Mockito.anyLong(), Mockito.anyLong())).willReturn(false);

        Image image = imageService.getRandomNonRatedImageFromCategory("Fish", 1L);

        //assertions
        assertEquals(testImage.getImageId(), image.getImageId());
    }


    @Test
    void getRandomNonRatedImageFromCategory_non_rated_does_not_exist() {

        // testUser
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        // when -> any object is being save in the userRepository -> return the dummy
        //Setup of the image and category
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testcategory);
        imageService.createImage(testImage, testUser);
        Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

        given(imageRepository.findRandomImageFromCategory(Mockito.anyString(), Mockito.anyInt())).willReturn(testImage);
        given(imageRepository.ratingCheck(Mockito.anyLong(), Mockito.anyLong())).willReturn(true);

        //assertions
        assertThrows(ResponseStatusException.class, () -> imageService.getRandomNonRatedImageFromCategory("Fish", 1L));
    }

    @Test
    void getRandomNonRatedImage_non_rated_exist() {
        // testUser
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        // when -> any object is being save in the userRepository -> return the dummy
        //Setup of the image and category
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testcategory);
        imageService.createImage(testImage, testUser);
        Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

        given(imageRepository.findRandomImage(Mockito.anyInt())).willReturn(testImage);
        given(imageRepository.ratingCheck(Mockito.anyLong(), Mockito.anyLong())).willReturn(false);

        Image image = imageService.getRandomNonRatedImage(1L);

        //assertions
        assertEquals(testImage.getImageId(), image.getImageId());
    }


    @Test
    void getRandomNonRatedImage_non_rated_does_not_exist() {
        // testUser
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        // when -> any object is being save in the userRepository -> return the dummy
        //Setup of the image and category
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testcategory);
        imageService.createImage(testImage, testUser);
        Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

        given(imageRepository.findRandomImage(Mockito.anyInt())).willReturn(testImage);
        given(imageRepository.ratingCheck(Mockito.anyLong(), Mockito.anyLong())).willReturn(true);

        //assertions
        assertThrows(ResponseStatusException.class, () -> imageService.getRandomNonRatedImage(1L));
    }

    @Test
    void getRandomImage() {
        // testUser
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        // when -> any object is being save in the userRepository -> return the dummy
        //Setup of the image and category
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testcategory);
        imageService.createImage(testImage, testUser);
        Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

        given(imageRepository.findRandomImage(Mockito.anyInt())).willReturn(testImage);

        Image image = imageService.getRandomImage();

        //assertions
        assertEquals(testImage.getImageId(), image.getImageId());
    }

    @Test
    void getHighlights() {
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        //Setup of the image and category
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testcategory);
        imageService.createImage(testImage, testUser);
        Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

        // create the image and get all the images
        // when -> any object is being save in the userRepository -> return the dummy
        List<Image> allImages = Collections.singletonList(testImage);

        given(imageRepository.findHighlightsFromCategory(Mockito.anyString())).willReturn((allImages));
        List<Image> image = imageService.getHighlights("Fish");

        //assertions
        assertEquals(image.get(0).getImageId(), testImage.getImageId());

    }

    @Test
    void updateImage() {
        //Mock
        given(imageRepository.findImageByImageId(Mockito.anyLong())).willReturn(testImage);

        //Updates for Image
        Image imageChanges = new Image();
        imageChanges.setImageId(1L);
        imageChanges.setName("updatedName");

        //Call to apply
        Image updatedImage = imageService.updateImage(testImage, imageChanges);

        //assertions
        assertEquals(updatedImage.getImageId(), testImage.getImageId());
        assertEquals(updatedImage.getName(), "updatedName");
    }

    @Test
    void updateClassification() {
        //This function is not used in the final version only helper to make coding easier
    }

    @Test
    void rateImage() {
        //testUser
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        //Setup
        ImagePutDTO rating = new ImagePutDTO();
        rating.setRating(5);
        rating.setImageId(1L);

        //Mock
        given(imageRepository.findImageByImageId(Mockito.anyLong())).willReturn(testImage);
        given(userRepository.findByUserId(1L)).willReturn(testUser);

        //Call to apply
        Image image = imageService.rateImage(rating, testUser.getUserId());

        //assertions
        assertEquals(image.getRating(), 5);
        assertEquals(image.getImageId(), testImage.getImageId());
    }

    @Test
    void addWhoRated() {
        //testUser
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        //Mock
        given(userRepository.findByUserId(Mockito.anyLong())).willReturn(testUser);


        imageService.addWhoRated(testImage, testUser.getUserId());
        //Gets from the relationship all users that rated that image
        User userObjectWhoRated = testImage.getRatedBy().iterator().next();

        //assertions
        assertEquals(testUser.getUserId(), userObjectWhoRated.getUserId());
    }

    @Test
    void applyBoost() {
        //test user
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        //Setup of the image and category
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testcategory);
        imageService.createImage(testImage, testUser);
        Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

        //Mock
        given(imageRepository.findImageByImageId(Mockito.anyLong())).willReturn(testImage);
        given(userRepository.findByUserId(Mockito.anyLong())).willReturn(testUser);

        //Call to apply
        imageService.applyBoost(testUser.getUserId(), testImage);

        //assertions
        assertEquals(testImage.getClassification(), Classification.A);
    }

    @Test
    void deleteImage() {
        //Nothing to be tested here

    }
}