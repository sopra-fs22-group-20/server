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

        newUser.setCreationDate(Current_Date.getSQLDate());

        // saves the given entity but data is only persisted in the database once
        // flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User updateUserInfo(User userToBeChanged, User userChanges) {
        //Checks if this user exists
        if (userToBeChanged == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("The user with this id does not exist!"));
        }

        //Parameter to update
        userToBeChanged.setMoreInfo(userChanges.getMoreInfo());
        userToBeChanged.setInstagram(userChanges.getInstagram());

        userRepository.save(userToBeChanged);
        userRepository.flush();

        log.debug("Updated information for User: {}", userToBeChanged);
        return userToBeChanged;
    }

    public User updateUserPassword(User userToBeChanged, User userChanges) {
        //Checks if this user exists
        if (userToBeChanged == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("The user with this id does not exist!"));
        }

        //Parameter to update
        userToBeChanged.setPassword(userChanges.getPassword());

        userRepository.save(userToBeChanged);
        userRepository.flush();

        log.debug("Updated information for User: {}", userToBeChanged);
        return userToBeChanged;
    }

    public User updateTrophies(User userInput) {
        checkTrophies(userInput);

        User user = userRepository.findByUserId(userInput.getUserId());
        user.setTrophies(userInput.getTrophies());

        userRepository.save(user);
        userRepository.flush();

        log.debug("Updated trophies for User: {}", user);
        return user;
    }

    public User loginUser(User loginUser) {
        User tempUser = userRepository.findByUsername(loginUser.getUsername());

        if (tempUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("username not found"));
        }

        //Check if the input password equals the one matching with the username
        if (tempUser.getPassword().equals(loginUser.getPassword())) {

            log.debug("Login worked {}", loginUser);
            return tempUser;
        }
        //if bug check for the exception
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("password does not match with username"));
    }

    public void checkIfUsernameExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());

        String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
        if (userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format(baseErrorMessage, "username", "is"));
        }
    }

    public void checkTrophies(User userInput) {
        if (userInput.getTrophies() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Number of Trophies is illegal"));
        }
    }

    public void checkIfEmailExists(User userToBeCreated) {
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
        checkIfUserIdExists(userId);

        userRepository.deleteUserByUserId(userId);
        log.debug("User deleted");
    }

    public void checkIfUserIdExists(Long userId) {
        //Checks if User exists
        if (userRepository.findByUserId(userId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("The User with this id does not exist!"));
        }
    }
}
