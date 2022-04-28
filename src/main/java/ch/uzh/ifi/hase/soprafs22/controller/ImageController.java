package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
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

    ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
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
     * Temp Create Image with Request Header
     * Post Nr. 3
     */
    @PostMapping("/images")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ImageGetDTO createImage(@RequestHeader(name = "userId") Long userId, @RequestBody ImagePostDTO imagePostDTO) {
        System.out.println(userId);
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
    public void rateImage(@RequestHeader(name = "userId") Long userId, @RequestBody ImagePutDTO rating){
        imageService.rateImage(rating, userId);
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
