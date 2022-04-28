package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.entity.Image;

import ch.uzh.ifi.hase.soprafs22.repository.ImageRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceTest {
    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    private Image testImage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testImage = new Image();
        testImage.setImageId(1l);
        testImage.setName("image");
        testImage.setLocation("location");
        testImage.setStorageLink("storage");
        testImage.setClassification("C");



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

        // create the image and get all the images

        Image createdImage = imageService.createImage(testImage, testUser);
        imageService.getAllImagesOfUser(1l);


    }
/**
    @Test
    void getImageByImageId() {
        // testUser
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        // when -> any object is being save in the userRepository -> return the dummy

        Image createdImage = imageService.createImage(testImage, testUser);
        System.out.println(createdImage);
        System.out.println(testImage.getImageId());
        System.out.println(imageService.getImageByImageId(1l));
                // then
        Mockito.verify(imageRepository, Mockito.times(1)).save(Mockito.any());

        //assertions
        //assertEquals(testImage.getImageId(), imageService.getImageByImageId(createdImage.getImageId()));
    }
**/
    @Test
    void checkAccess() {
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

    @Test
    void updateImage() {
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
        imageService.updateImage(testImage,testImageUpdate);

    }
/**
    @Test
    void deleteImage() {
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