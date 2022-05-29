package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.constant.Classification;
import ch.uzh.ifi.hase.soprafs22.constant.Current_Date;
import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ImageRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ImageRepository imageRepository;

    //Don't change order because ID are auto generated Like this

    @Test
    public void findImageByImageId_success() {
        //setup
        User owner = new User("username", "password", "email", "moreInfo");
        owner.setToken("9");

        //given
        Image image = new Image();
        image.setName("imageName");
        image.setOwner(owner);
        image.setLocation("location");
        image.setStorageLink("storageLink");

        entityManager.persist(owner);
        entityManager.flush();
        entityManager.persist(image);
        entityManager.flush();

        // when
        Image found = imageRepository.findImageByImageId(image.getImageId());

        // then
        assertNotNull(found.getImageId());
        assertEquals(found.getName(), image.getName());
        assertEquals(found.getOwner(), image.getOwner());
        assertEquals(found.getLocation(), image.getLocation());
        assertEquals(found.getStorageLink(), image.getStorageLink());

    }

    @Test
    public void findImageByStorageLink() {
        //setup
        User owner = new User("username", "password", "email", "moreInfo");
        owner.setToken("10");

        //given
        Image image = new Image();
        image.setName("imageName");
        image.setOwner(owner);
        image.setLocation("location");
        image.setStorageLink("storageLink");

        entityManager.persist(owner);
        entityManager.flush();
        entityManager.persist(image);
        entityManager.flush();

        // when
        Image found = imageRepository.findImageByStorageLink(image.getStorageLink());

        // then
        assertNotNull(found.getImageId());
        assertEquals(found.getName(), image.getName());
        assertEquals(found.getOwner(), image.getOwner());
        assertEquals(found.getLocation(), image.getLocation());
        assertEquals(found.getStorageLink(), image.getStorageLink());

    }

    @Test
    public void findImagesByOwnerUserId() {
        //setup
        User owner = new User("username", "password", "email", "moreInfo");
        owner.setToken("11");

        //given
        Image image = new Image();
        image.setName("imageName");
        image.setOwner(owner);
        image.setLocation("location");
        image.setStorageLink("storageLink");

        entityManager.persist(owner);
        entityManager.flush();
        entityManager.persist(image);
        entityManager.flush();

        // when
        List<Image> found = imageRepository.findImagesByOwnerUserId(image.getOwner().getUserId());

        // then
        assertNotNull(found.get(0).getImageId());
        assertEquals(found.get(0).getName(), image.getName());
        assertEquals(found.get(0).getLocation(), image.getLocation());
        assertEquals(found.get(0).getStorageLink(), image.getStorageLink());
    }

    @Test
    public void deleteImageByImageId() {
        //setup
        User owner = new User("username", "password", "email", "moreInfo");
        owner.setToken("12");

        //given
        //imageId = 4L
        Image image = new Image();
        image.setName("imageName");
        image.setOwner(owner);
        image.setLocation("location");
        image.setStorageLink("storageLink");

        entityManager.persist(owner);
        entityManager.flush();
        entityManager.persist(image);
        entityManager.flush();

        //get imageId
        Long id = imageRepository.findImageByStorageLink("storageLink").getImageId();

        imageRepository.deleteImageByImageId(id);

        Image found = imageRepository.findImageByImageId(id);

        //then
        assertNull(found);
    }

    @Test
    public void findRandomImage() {
        //setup
        User owner = new User("username", "password", "email", "moreInfo");
        owner.setToken("13");

        //given
        Image image = new Image();
        image.setName("imageName");
        image.setOwner(owner);
        image.setLocation("location");
        image.setStorageLink("storageLink");
        image.setClassification(Classification.A);

        entityManager.persist(owner);
        entityManager.flush();
        entityManager.persist(image);
        entityManager.flush();

        Image found = imageRepository.findRandomImage(0);

        // then
        assertNotNull(found.getImageId());
        assertEquals(found.getName(), image.getName());
        assertEquals(found.getOwner(), image.getOwner());
        assertEquals(found.getLocation(), image.getLocation());
        assertEquals(found.getStorageLink(), image.getStorageLink());
    }

    @Test
    public void findRandomImageFromCategory() {
        //setup
        User owner = new User("username", "password", "email", "moreInfo");
        owner.setToken("13");

        Category category = new Category("Fish");


        //given
        Image image = new Image();
        image.setName("imageName");
        image.setOwner(owner);
        image.setLocation("location");
        image.setStorageLink("storageLink");
        image.setClassification(Classification.A);
        image.setCategory(category);

        entityManager.persist(owner);
        entityManager.flush();
        entityManager.persist(image);
        entityManager.flush();

        Image found = imageRepository.findRandomImageFromCategory("Fish", 0);

        // then
        assertNotNull(found.getImageId());
        assertEquals(found.getName(), image.getName());
        assertEquals(found.getOwner(), image.getOwner());
        assertEquals(found.getLocation(), image.getLocation());
        assertEquals(found.getStorageLink(), image.getStorageLink());
    }

    @Test
    public void findHighlightsFromCategory() {
        /**
         //setup
         User owner = new User("username", "password", "email", "moreInfo");
         owner.setToken("13");

         Category category = new Category();
         category.setName("Fish");


         //given
         Image image1 = new Image();
         image1.setName("imageName1");
         image1.setOwner(owner);
         image1.setLocation("location1");
         image1.setStorageLink("storageLink1");
         image1.setClassification(Classification.A);
         image1.setCategory(category);
         image1.setRating(5);

         Image image2 = new Image();
         image2.setName("imageName2");
         image2.setOwner(owner);
         image2.setLocation("location2");
         image2.setStorageLink("storageLink2");
         image2.setClassification(Classification.A);
         image2.setCategory(category);
         image2.setRating(4);

         Set<Image> images = new HashSet<>();
         images.add(image1);
         images.add(image2);

         category.setImages(images);


         entityManager.persist(owner);
         entityManager.flush();
         entityManager.persist(image1);
         entityManager.flush();
         entityManager.persist(image2);
         entityManager.flush();

         //when
         List<Image> found = imageRepository.findHighlightsFromCategory("Fish");

         // then
         assertNotNull(found.get(1).getImageId());
         //assertNotNull(found.get(1).getImageId());
         assertEquals(found.get(1).getName(), image1.getName());
         //assertEquals(found.get(1).getName(), image1.getName());
         */
    }

    @Test
    public void ratingCheck_not_rated() {
        //setup
        User owner = new User("username", "password", "email", "moreInfo");
        owner.setToken("13");

        Category category = new Category("Fish");

        //given
        Image image = new Image();
        image.setName("imageName");
        image.setOwner(owner);
        image.setLocation("location");
        image.setStorageLink("storageLink");
        image.setClassification(Classification.A);
        image.setCategory(category);

        entityManager.persist(owner);
        entityManager.flush();
        entityManager.persist(image);
        entityManager.flush();

        //when
        Boolean answer = imageRepository.ratingCheck(owner.getUserId(), image.getImageId());

        //assertion
        assertEquals(false, answer);
    }

    @Test
    public void checkForBoost() {
        //setup
        User owner = new User("username", "password", "email", "moreInfo");
        owner.setToken("13");

        Category category = new Category("Fish");

        //given
        Image image = new Image();
        image.setName("imageName");
        image.setOwner(owner);
        image.setLocation("location");
        image.setStorageLink("storageLink");
        image.setClassification(Classification.A);
        image.setCategory(category);
        image.setBoostDate(Current_Date.getSQLDate());

        entityManager.persist(owner);
        entityManager.flush();
        entityManager.persist(image);
        entityManager.flush();

        //when
        Boolean answer = imageRepository.checkForBoost(image.getImageId());

        //assertion
        assertEquals(true, answer);

    }
}