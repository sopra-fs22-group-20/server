package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.Current_Date;
import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.CategoryRepository;
import ch.uzh.ifi.hase.soprafs22.repository.ImageRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImagePutDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ImageService(@Qualifier("imageRepository") ImageRepository imageRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Image createImage(Image newImage, User owner) {
        newImage.setUploadDate(Current_Date.getDate());
        newImage.setOwner(owner);

        //Check if the category passed on exists
        Category tempCategory = categoryRepository.findByName(newImage.getCategory().getName());
        if (tempCategory == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("This category does not exist"));
        }

        //Checks if the storageLink exists
        Image tempImage = imageRepository.findImageByStorageLink(newImage.getStorageLink());
        if (tempImage != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("The image with this firebase-storageLink already exist!"));
        }
        newImage.setCategory(tempCategory);
        newImage = imageRepository.save(newImage);
        imageRepository.flush();

        log.debug("Created Information for Image: {}", newImage);
        return newImage;
    }

    public List<Image> getAllImagesOfUser(long userId) {
        return this.imageRepository.findImagesByOwnerUserId(userId);
    }

    public Image getImageByImageId(long imageId) {
        checkIfImageExists(imageId);

        Image tempImage = imageRepository.findImageByImageId(imageId);
        return tempImage;
    }

    public Image getRandomImage() {
        return imageRepository.findRandomImage();
    }

    public List<Image> getHighlights(String category) {
        return this.imageRepository.findHighlightsFromCategory(category);
    }

    public Image updateImage(Image imageToBeChanged, Image imageChanges) {
        //Checks if this image exists
        checkIfImageExists(imageToBeChanged.getImageId());

        //Parameter to update
        imageToBeChanged.setName(imageChanges.getName());

        imageRepository.save(imageToBeChanged);
        imageRepository.flush();

        log.debug("Updated information for image: {}", imageToBeChanged);
        return imageToBeChanged;
    }

    public Image rateImage(ImagePutDTO rating, Long userId) {
        checkIfImageExists(rating.getImageId());
        checkIfRatingInputIsLegal(rating.getRating());
        Image ratedImage = imageRepository.findImageByImageId(rating.getImageId());

        //calculate new rating score and insert it into the image field
        ratedImage.setRating(calculateNewRatingScore(ratedImage.getRating(), rating.getRating(), ratedImage.getRatingCounter()));

        //Adding one more rate to the counter
        ratedImage.setRatingCounter(ratedImage.getRatingCounter() + 1);

        //Adding to the Many-to-Many relation
        addWhoRated(ratedImage, userId);
        return ratedImage;
    }

    //Adds who rated what to the Many-to-Many relation
    public void addWhoRated(Image image, Long userId) {

        User ratingUser = userRepository.findByUserId(userId);

        //Image side
        Set<User> tempUserSet = new HashSet<User>();

        if (image.getRatedBy() != null) {
            tempUserSet = image.getRatedBy();
        }
        tempUserSet.add(ratingUser);
        image.setRatedBy(tempUserSet);

        //User side
        Set<Image> tempImageSet = new HashSet<Image>();

        if (ratingUser.getImagesRated() != null) {
            tempImageSet = ratingUser.getImagesRated();
        }
        tempImageSet.add(image);
        ratingUser.setImagesRated(tempImageSet);

        imageRepository.save(image);
        imageRepository.flush();

        userRepository.save(ratingUser);
        userRepository.flush();
    }

    public void deleteImage(Long imageId) {
        //Checks if this image exists
        checkIfImageExists(imageId);

        imageRepository.deleteImageByImageId(imageId);
        log.debug("Image deleted");
    }

    //Helpers
    public void checkAccess(Long userId, Image image) {
        if (!Objects.equals(image.getOwner().getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    String.format("You dont have access"));
        }
    }

    public void checkIfImageExists(Long imageId) {
        //Checks if this image exists
        if (imageRepository.findImageByImageId(imageId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("The image with this id does not exist!"));
        }
    }

    public void checkIfRatingInputIsLegal(double rating) {
        if (1 > rating || rating > 5) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("This rating is not possible. It is larger than 5 or smaller than 1"));
        }
    }

    private double calculateNewRatingScore(double currentRating, double newRating, int ratingCount) {
        //Multiply rating by amount of ratings plus new rating divided by new amount of ratings
        return ((currentRating * ratingCount + newRating) / (ratingCount + 1));
    }
}
