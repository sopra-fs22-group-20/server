package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.Current_Date;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUserByUserId(long userId) {
        User tempUser = userRepository.findByUserId(userId);

        //check if the user even exists -> extract later on
        if (tempUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("The user with this id does not exist!"));
        }
        return tempUser;
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());

        checkIfUsernameExists(newUser);
        checkIfEmailExists(newUser);

        newUser.setCreationDate(Current_Date.getDate());

        // saves the given entity but data is only persisted in the database once
        // flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User updateUser(User userToBeChanged, User userChanges) {
        //Checks if this user exists
        if (userToBeChanged == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("The user with this id does not exist!"));
        }

        //Parameter to update
        userToBeChanged.setMoreInfo(userChanges.getMoreInfo());

        userRepository.save(userToBeChanged);
        userRepository.flush();

        log.debug("Updated information for User: {}", userToBeChanged);
        return userToBeChanged;
    }

    public User loginUser(User loginUser) {
        if (loginUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("username not found"));
        }
        User tempUser = userRepository.findByUsername(loginUser.getUsername());

        //Check if the input password equals the one matching with the username
        if (tempUser.getPassword().equals(loginUser.getPassword())) {

            log.debug("Login worked {}", loginUser);
            return tempUser;
        }
        //if bug check for the exception
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("password does not match with username"));
    }

    /**
     * This is a helper method that will check the uniqueness criteria of the
     * username and the name
     * defined in the User entity. The method will do nothing if the input is unique
     * and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws org.springframework.web.server.ResponseStatusException
     * @see User
     */
    private void checkIfUsernameExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());

        String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
        if (userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format(baseErrorMessage, "username", "is"));
        }
    }

    private void checkIfEmailExists(User userToBeCreated) {
        User userByEmail = userRepository.findByEmail(userToBeCreated.getEmail());

        String baseErrorMessage = "The %s provided %s already taken. Therefore, the user could not be created!";
        if (userByEmail != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format(baseErrorMessage, "Email", "is"));
        }
    }

    public void checkAccess(Long pingingUserId, Long LoggedInUserId) {
        if (!Objects.equals(pingingUserId, LoggedInUserId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    String.format("You dont have access to edit this user"));
        }
    }

    public void deleteUser(Long userId) {
        //Checks if User exists
        if (userRepository.findByUserId(userId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("The User with this id does not exist!"));
        }

        userRepository.deleteUserByUserId(userId);
        System.out.println(userRepository.count());
        System.out.println("deleted User");
        log.debug("User deleted");
    }
}
