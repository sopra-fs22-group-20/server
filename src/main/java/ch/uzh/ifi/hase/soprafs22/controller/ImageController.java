package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImageGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImagePostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImagePutDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.CategoryService;
import ch.uzh.ifi.hase.soprafs22.service.ImageService;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;
    private final CategoryService categoryService;

    ImageController(ImageService imageService, UserService userService, CategoryService categoryService) {
        this.imageService = imageService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    /**
     * Returns a specific image
     * Get Nr. 3
     */
    @GetMapping("/images/{imageId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ImageGetDTO getImage(@PathVariable long imageId) {
        Image tempImage = imageService.getImageByImageId(imageId);
        ImageGetDTO image = DTOMapper.INSTANCE.convertEntityToImageGetDTO(tempImage);
        return image;
    }

    /**
     * Returns all images of a user
     * Get Nr. 4
     */
    @GetMapping("/images/all/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ImageGetDTO> getAllImagesOfUser(@PathVariable long userId) {
        // fetch all images of a user in the internal representation
        List<Image> images = imageService.getAllImagesOfUser(userId);
        List<ImageGetDTO> imageGetDTOs = new ArrayList<>();

        // convert each image to the API representation
        for (Image image : images) {
            imageGetDTOs.add(DTOMapper.INSTANCE.convertEntityToImageGetDTO(image));
        }
        return imageGetDTOs;
    }

    /**
     * Returns the highlights from a category
     * Get Nr. 5
     */
    @GetMapping("/images/highlights/{category}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ImageGetDTO> getHighlightsFromCategory(@PathVariable String category) {
        //Fetch highlights from a category
        List<Image> highlights = imageService.getHighlights(category);
        List<ImageGetDTO> imageGetDTOs = new ArrayList<>();

        // convert each image to the API representation
        for (Image image : highlights) {
            imageGetDTOs.add(DTOMapper.INSTANCE.convertEntityToImageGetDTO(image));
        }
        return imageGetDTOs;
    }

    /**
     * Returns a random image the user has not seen yet
     * Get Nr. 7
     */
    @GetMapping("/images/random/{category}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ImageGetDTO getNonRatedImageFromCategory(@PathVariable String category, @RequestHeader(name = "userId") Long userId) {
        Image randomNonRatedImage = imageService.getRandomNonRatedImageFromCategory(category, userId);
        return DTOMapper.INSTANCE.convertEntityToImageGetDTO(randomNonRatedImage);
    }

    /**
     * Returns a random image
     * Get Nr. 8
     */
    @GetMapping("/images/random/c")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ImageGetDTO getNonRatedRandomImage(@RequestHeader(name = "userId") Long userId) {
        Image randomNonRatedImage = imageService.getRandomNonRatedImage(userId);
        return DTOMapper.INSTANCE.convertEntityToImageGetDTO(randomNonRatedImage);
    }

    /**
     * Returns a random image
     * Get Nr. 9
     */
    @GetMapping("/images/random")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ImageGetDTO getRandomImage() {
        Image randomImage = imageService.getRandomImage();
        return DTOMapper.INSTANCE.convertEntityToImageGetDTO(randomImage);
    }

    /**
     * Checks if a user has seen a specific image
     * Get Nr. 10
     */
    @GetMapping("/images/check/{imageId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void checkIfRated(@RequestHeader(name = "userId") Long userId, @PathVariable Long imageId) {
        imageService.checkIfRated(userId, imageId);
    }

    /**
     * Create Image with Request Header
     * Post Nr. 3
     */
    @PostMapping("/images")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ImageGetDTO createImage(@RequestHeader(name = "userId") Long userId, @RequestBody ImagePostDTO imagePostDTO) {
        Image imageInput = DTOMapper.INSTANCE.convertImagePostDTOtoEntity(imagePostDTO);

        //Get the user from the cookies of the localstorage via userId
        User owner = userService.getUserByUserId(userId);

        //Create the image entity
        Image createImage = imageService.createImage(imageInput, owner);

        return DTOMapper.INSTANCE.convertEntityToImageGetDTO(createImage);
    }

    /**
     * Updates Image Info
     * Put Nr. 2
     */
    @PutMapping("/images/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ImageGetDTO updateImage(@RequestHeader(name = "userId") Long userId, @RequestBody ImagePutDTO changes, @PathVariable Long imageId) {
        Image imageChanges = DTOMapper.INSTANCE.convertImagePutDTOtoEntity(changes);

        Image imageToBeChanged = imageService.getImageByImageId(imageId);

        //Create a check if change is allowed
        imageService.checkAccess(userId, imageToBeChanged);

        Image imageUpdate = imageService.updateImage(imageToBeChanged, imageChanges);

        return DTOMapper.INSTANCE.convertEntityToImageGetDTO(imageUpdate);
    }

    /**
     * Updates rating of an image
     * Put Nr. 3
     */
    @PutMapping("/rate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void rateImage(@RequestHeader(name = "userId") Long userId, @RequestBody ImagePutDTO rating) {
        imageService.rateImage(rating, userId);
    }

    /**
     * Apply boost
     * Put Nr. 5
     */
    @PutMapping("/images/boost")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void boost(@RequestHeader(name = "userId") Long userId, @RequestBody ImagePutDTO image) {
        imageService.checkTrophies(userId);
        Image imageToBoost = DTOMapper.INSTANCE.convertImagePutDTOtoEntity(image);
        imageService.applyBoost(userId, imageToBoost);
    }

    /**
     * Update Classification
     * Put Nr. 6
     */
    @PutMapping("/classification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ImageGetDTO updateClassification(@RequestBody ImagePutDTO imageClassification, @RequestHeader(name = "userId") Long userId) {
        Image image = DTOMapper.INSTANCE.convertImagePutDTOtoEntity(imageClassification);
        //imageService.checkAccess(userId, image);
        Image imageUpdate = imageService.updateClassification(image);
        return DTOMapper.INSTANCE.convertEntityToImageGetDTO(imageUpdate);
    }


    /**
     * Deletes an Image
     * Delete Nr. 2
     */
    @DeleteMapping("/images/{imageId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void deleteImage(@RequestHeader(name = "userId") Long userId, @PathVariable Long imageId) {
        Image imageToBeDeleted = imageService.getImageByImageId(imageId);

        //Create a deletion if change is allowed
        imageService.checkAccess(userId, imageToBeDeleted);
        imageService.deleteImage(imageId);
    }
}
