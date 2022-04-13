package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImageGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImagePostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
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
}
