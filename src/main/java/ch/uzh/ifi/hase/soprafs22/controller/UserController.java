package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;


    UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    /**
     * Returns all users
     * Get Nr. 1
     */
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetDTO> getAllUsers() {
        // fetch all users in the internal representation
        List<User> users = userService.getUsers();
        List<UserGetDTO> userGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (User user : users) {
            userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOs;
    }

    /**
     * Returns information of a specific user
     * Get Nr. 2
     */
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO getUsers(@PathVariable long userId) {
        User tempUser = userService.getUserByUserId(userId);
        UserGetDTO user = DTOMapper.INSTANCE.convertEntityToUserGetDTO(tempUser);
        return user;
    }

    /**
     * Adds a new user to the Database and returns this user
     * Post Nr. 1
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // create user
        User createdUser = userService.createUser(userInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
    }

    /**
     * Checks if the login credentials are correct
     * Post Nr. 2
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO loginUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // create user
        User loggedInUser = userService.loginUser(userInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loggedInUser);
    }

    /**
     * Updates User Info
     * Put Nr. 1a
     */
    @PutMapping("/users/info/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public UserGetDTO updateUserInfo(@RequestHeader(name = "userId") Long loggedInUserId, @RequestBody UserPutDTO changes, @PathVariable Long userId) {
        User userChanges = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(changes);

        //Check if the user that is edited is also the user that is logged in
        userService.checkAccess(userId, loggedInUserId);

        //Sets the inputUser to the one accordingly to the PathVariable
        User userToBeChanged = userService.getUserByUserId(userId);


        User userUpdate = userService.updateUserInfo(userToBeChanged, userChanges);

        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userUpdate);
    }

    /**
     * Updates User Info
     * Put Nr. 1b
     */
    @PutMapping("/users/credentials/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public UserGetDTO updateUserPassword(@RequestHeader(name = "userId") Long loggedInUserId, @RequestBody UserPutDTO changes, @PathVariable Long userId) {
        User userChanges = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(changes);

        //Check if the user that is edited is also the user that is logged in
        userService.checkAccess(userId, loggedInUserId);

        //Sets the inputUser to the one accordingly to the PathVariable
        User userToBeChanged = userService.getUserByUserId(userId);


        User userUpdate = userService.updateUserPassword(userToBeChanged, userChanges);

        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userUpdate);
    }

    /**
     * Updates User trophies
     * Put Nr. 4
     */
    @PutMapping("/users/trophies")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public UserGetDTO updateTrophies(@RequestBody UserPutDTO user) {

        User userToUpdate = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(user);

        User updatedUser = userService.updateTrophies(userToUpdate);

        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(updatedUser);
    }


    /**
     * Deletes an User and all his Images
     * Delete Nr. 1
     */
    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void deleteUser(@RequestHeader(name = "userId") Long loggedInUserId, @PathVariable Long userId) {
        //Create a deletion if change is allowed
        userService.checkAccess(userId, loggedInUserId);

        userService.deleteUser(userId);
    }
}
