package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setUserId(1l);
        testUser.setEmail("test@mail.com");
        testUser.setPassword("password");

        // when -> any object is being save in the userRepository -> return the dummy
        // testUser
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
    }

    @Test
    void getUsers() {

        List<User> allUsers = Collections.singletonList(testUser);

        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(userRepository.findAll()).willReturn(allUsers);

        //assertions
        assertEquals(testUser.getUserId(), allUsers.get(0).getUserId());
        assertEquals(testUser.getUsername(), allUsers.get(0).getUsername());
        assertEquals(testUser.getPassword(), allUsers.get(0).getPassword());
        assertEquals(testUser.getEmail(), allUsers.get(0).getEmail());
        assertEquals(testUser.getMoreInfo(), allUsers.get(0).getMoreInfo());
        assertEquals(testUser.getHighlightCounter(), allUsers.get(0).getHighlightCounter());
    }

    @Test
    void getUsersByUserId_found() {

        given(userRepository.findByUserId(Mockito.anyLong())).willReturn(testUser);

        assertEquals(userRepository.findByUserId(1L), testUser);
    }

    @Test
    void getUsersByUserId_not_found() {

        given(userRepository.findByUserId(Mockito.anyLong())).willReturn(null);

        assertThrows(ResponseStatusException.class, () -> userService.getUserByUserId(1L));
    }

    @Test
    public void createUser_validInputs_success() {
        // when -> any object is being save in the userRepository -> return the dummy
        // testUser
        User createdUser = userService.createUser(testUser);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

        //assertions
        assertEquals(testUser.getUserId(), createdUser.getUserId());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertEquals(testUser.getEmail(), createdUser.getEmail());
        assertEquals(testUser.getPassword(), createdUser.getPassword());
        assertNotNull(createdUser.getToken());
    }

    @Test
    public void createUser_duplicateInputs_throwsException() {
        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

        // then -> attempt to create second user with same user -> check that an error
        // is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
    }

    @Test
    public void updateUserInfo() {
        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

        // then -> update User
        User testUserUpdate = testUser;
        testUserUpdate.setMoreInfo("updatedUserInfo");
        testUserUpdate.setInstagram("instagram");
        userService.updateUserInfo(testUser, testUserUpdate);

        //assert, that the MoreInfo is now updated of testUser
        assertEquals(testUser.getMoreInfo(), testUserUpdate.getMoreInfo());
        assertEquals(testUser.getInstagram(), testUserUpdate.getInstagram());
    }

    @Test
    public void updateUserInfo_no_user() {
        //Setup
        User userToBeChanged = null;

        User userChanges = new User();
        userChanges.setMoreInfo("Hi");
        userChanges.setInstagram("insta");

        // is thrown
        assertThrows(ResponseStatusException.class, () -> userService.updateUserInfo(userToBeChanged, userChanges));
    }

    @Test
    public void updateUserPassword() {
        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

        // then -> update User
        User userUpdates = new User();
        userUpdates.setPassword("newPassword");
        userService.updateUserPassword(testUser, userUpdates);

        //assert, that the MoreInfo is now updated of testUser
        assertEquals("newPassword", userUpdates.getPassword());
    }

    @Test
    public void updateUserPassword_no_user() {
        //Setup
        User userToBeChanged = null;

        User userChanges = new User();
        userChanges.setPassword("newPassword");

        // is thrown
        assertThrows(ResponseStatusException.class, () -> userService.updateUserPassword(userToBeChanged, userChanges));
    }

    @Test
    public void updateTrophies() {
        User userInput = new User();
        userInput.setUserId(1L);
        userInput.setTrophies(10);

        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUserId(Mockito.anyLong())).thenReturn(testUser);

        //Does it
        userService.updateTrophies(userInput);

        assertEquals(testUser.getTrophies(), 10);
    }

    @Test
    public void loginUser() {
        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

        // then -> login user

        //assert, that the login was successfully and the user is returned
        assertEquals(userService.loginUser(testUser), testUser);
    }

    @Test
    public void checkIfUsernameExists_does_exist() {
        User userToBeCreated = new User();
        userToBeCreated.setUsername("testUsername");

        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

        assertThrows(ResponseStatusException.class, () -> userService.checkIfUsernameExists(userToBeCreated));
    }

    @Test
    public void checkTrophies_invalid_input() {
        User userInput = new User();
        userInput.setTrophies(-10);

        assertThrows(ResponseStatusException.class, () -> userService.checkTrophies(userInput));
    }

    @Test
    public void checkIfEmailExists_true() {
        User userInput = new User();
        userInput.setEmail("test@mail.com");

        // given -> a first user has already been created
        userService.createUser(testUser);

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(testUser);

        assertThrows(ResponseStatusException.class, () -> userService.checkIfEmailExists(userInput));

    }

    @Test
    public void checkAccess() {
        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

        //assert, that the access works, if the id are the same
        userService.checkAccess(testUser.getUserId(), testUser.getUserId());
    }

    @Test
    public void checkAccess_denied() {
        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> userService.checkAccess(testUser.getUserId(), 2L));
    }

    @Test
    public void deleteUser() {
        //Nothing to be tested here
    }

    @Test
    public void checkIfUserIdExists() {
        User userInput = new User();
        userInput.setUserId(2L);

        // given -> a first user has already been created
        userService.createUser(testUser);

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> userService.checkIfUserIdExists(userInput.getUserId()));
    }
}
