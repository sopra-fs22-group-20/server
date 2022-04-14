package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.ImageService;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
     * Adds a new image to the Database and returns this image entity
     * Post Nr. 3
     */
    @PostMapping("/images")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    //@RequestHeader(name = "userId") Long userId,
    public ImageGetDTO createPicture(@CookieValue(name = "userId") Long userId, @RequestBody ImagePostDTO imagePostDTO) {
        Image imageInput = DTOMapper.INSTANCE.convertImagePostDTOtoEntity(imagePostDTO);
        //Get the user from the cookies of the localstorage via userId
        User owner = userService.getUserByUserId(userId);
        //Create the image entity
        Image createImage = imageService.createImage(imageInput, owner);

        return DTOMapper.INSTANCE.convertEntityToImageGetDTO(createImage);
    }

    /**
     * Temp Create Image with Request Header
     * Post Nr. 3
     */
    @PostMapping("/imagesTemp")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    //@RequestHeader(name = "userId") Long userId,
    public ImageGetDTO createPictureTemp(@RequestHeader(name = "userId") Long userId, @RequestBody ImagePostDTO imagePostDTO) {
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
    public ImageGetDTO updateImage(@RequestBody ImagePutDTO changes, @PathVariable Long imageId, @RequestHeader(name = "userId") Long userId) {
        Image imageChanges = DTOMapper.INSTANCE.convertImagePutDTOtoEntity(changes);

        Image imageToBeChanged = imageService.getImageByImageId(imageId);

        //Create a check if change is allowed
        imageService.checkAccess(userId, imageToBeChanged);


        Image imageUpdate = imageService.updateImage(imageToBeChanged, imageChanges);

        return DTOMapper.INSTANCE.convertEntityToImageGetDTO(imageUpdate);

    }
}
