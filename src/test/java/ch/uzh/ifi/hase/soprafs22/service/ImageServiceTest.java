package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.Classification;
import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.entity.Image;

import ch.uzh.ifi.hase.soprafs22.repository.CategoryRepository;
import ch.uzh.ifi.hase.soprafs22.repository.ImageRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImagePutDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


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

    private Category testCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        testCategory = new Category();
        testCategory.setName("Fish");

        // given
        testImage = new Image();
        testImage.setImageId(1l);
        testImage.setName("image");
        testImage.setLocation("location");
        testImage.setStorageLink("storage");
        testImage.setClassification(Classification.C);
        testImage.setCategory(testCategory);


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
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testCategory);

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
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testCategory);
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
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testCategory);
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
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testCategory);
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
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testCategory);
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
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testCategory);
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
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testCategory);
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
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testCategory);
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
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testCategory);
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
        //Mock
        given(imageRepository.findImageByImageId(Mockito.anyLong())).willReturn(testImage);

        //Updates for Image
        Image imageChanges = new Image();
        imageChanges.setImageId(1L);
        imageChanges.setClassification(Classification.A);

        //Call to apply
        Image updatedImage = imageService.updateClassification(imageChanges);

        //assertions
        assertEquals(updatedImage.getClassification(), Classification.A);
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
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(testCategory);
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

    @Test
    void checkForBoost() {
        //Mock
        given(imageRepository.checkForBoost(Mockito.anyLong())).willReturn(true);

        //assertion
        assertThrows(ResponseStatusException.class, () -> imageService.checkForBoost(Mockito.anyLong()));
    }

    @Test
    void resetAllExpiredBoosts() {
        //setup
        testImage.setClassification(Classification.A);
        List<Image> expiredBoostImages = Collections.singletonList(testImage);

        //Mock
        given(imageRepository.checkClassifications()).willReturn(expiredBoostImages);

        //apply
        imageService.resetAllExpiredBoosts();

        //assertions
        assertEquals(testImage.getClassification(), Classification.C);
    }

    @Test
    void checkAccess() {
        //testUser
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        //setup
        testImage.setOwner(testUser);

        //call and IDs do not match and assertion
        assertThrows(ResponseStatusException.class, () -> imageService.checkAccess(2L, testImage));
    }

    @Test
    void checkCategoriesExistence_does_not_exist() {
        //Mock
        given(categoryRepository.findByName(Mockito.anyString())).willReturn(null);

        //assertion
        assertThrows(ResponseStatusException.class, () -> imageService.checkCategoriesExistence(Mockito.anyString()));
    }

    @Test
    void checkIfImageExists() {
        //Mock
        given(imageRepository.findImageByImageId(Mockito.anyLong())).willReturn(null);

        //assertion
        assertThrows(ResponseStatusException.class, () -> imageService.checkIfImageExists(Mockito.anyLong()));
    }

    @Test
    void checkForNull() {
        //Setup
        Image image = null;

        //assertion
        assertThrows(ResponseStatusException.class, () -> imageService.checkForNull(image));
    }

    @Test
    void checkIfRatingInputIsLegal_to_small() {
        //assertion
        assertThrows(ResponseStatusException.class, () -> imageService.checkIfRatingInputIsLegal(0));
    }

    @Test
    void checkIfRatingInputIsLegal_to_large() {
        //assertion
        assertThrows(ResponseStatusException.class, () -> imageService.checkIfRatingInputIsLegal(6));
    }

    @Test
    void checkIfRated_true() {
        //Mock
        given(imageRepository.ratingCheck(Mockito.anyLong(), Mockito.anyLong())).willReturn(true);

        //assertion
        assertThrows(ResponseStatusException.class, () -> imageService.checkIfRated(Mockito.anyLong(), Mockito.anyLong()));
    }

    @Test
    void checkTrophies_insufficient() {
        //Setup
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");
        testUser.setTrophies(9);

        //Mock
        given(userRepository.findByUserId(Mockito.anyLong())).willReturn(testUser);

        //assertion
        assertThrows(ResponseStatusException.class, () -> imageService.checkTrophies(Mockito.anyLong()));
    }

    @Test
    void calculateNewRatingScore() {
        //assertion
        assertEquals(imageService.calculateNewRatingScore(4.0, 1.0,1), 2.5);
    }

    @Test
    void getWeightedClassification() {
        //assertion
        assertThat(imageService.getWeightedClassification(), Matchers.either(Matchers.is(0)).or(Matchers.is(1)));
    }
}