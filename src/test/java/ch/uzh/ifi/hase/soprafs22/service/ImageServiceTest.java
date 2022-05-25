package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.Classification;
import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.entity.Image;

import ch.uzh.ifi.hase.soprafs22.repository.CategoryRepository;
import ch.uzh.ifi.hase.soprafs22.repository.ImageRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class ImageServiceTest {
    @Mock
    private ImageRepository imageRepository;

    @Mock
    private CategoryRepository categoryRepository;

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

        assertEquals(testImage.getImageId(), image.getImageId());
    }

    @Test
    void getHighlights() {

    }

    @Test
    void updateImage() {

    }
/**
 @Test void checkAccess() {
 // testUser
 User testUser = new User();
 testUser.setUsername("testUsername");
 testUser.setUserId(1l);
 testUser.setEmail("test@mail.com");
 testUser.setPassword("password");

 Image createdImage = imageService.createImage(testImage, testUser);

 // when -> setup additional mocks for imageRepository
 Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

 //assert, that the access works, if the id are the same
 imageService.checkAccess(testImage.getImageId(),createdImage);
 }

 @Test void updateImageDoesNotExist() throws Exception {
 // testUser
 User testUser = new User();
 testUser.setUsername("testUsername");
 testUser.setUserId(1l);
 testUser.setEmail("test@mail.com");
 testUser.setPassword("password");

 Image createdImage = imageService.createImage(testImage, testUser);

 // when -> setup additional mocks for imageRepository
 Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());


 // then -> update image
 Image testImageUpdate  = createdImage;
 testImageUpdate.setName("UpdateImageName");

 }
 /**
 @Test void deleteImage() {
 // testUser
 User testUser = new User();
 testUser.setUsername("testUsername");
 testUser.setUserId(1l);
 testUser.setEmail("test@mail.com");
 testUser.setPassword("password");

 Image createdImage = imageService.createImage(testImage, testUser);

 // when -> setup additional mocks for imageRepository
 Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

 //delete Image

 System.out.println(imageService.getAllImagesOfUser(testUser.getUserId()));
 //imageService.deleteImage(testImage.getImageId());
 }
 **/
}