package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the UserController works.
 */

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserPostDTO userPostDTO;

    @Test
    public void getAllUsers() throws Exception {
        // given
        User user = new User();
        user.setUsername("Username");
        user.setPassword("Password");
        user.setEmail("Mail@mail.com");
        user.setMoreInfo("IG: UserIG");
        user.setHighlightCounter(0);

        List<User> allUsers = Collections.singletonList(user);

        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(userService.getUsers()).willReturn(allUsers);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())));

    }

    @Test
    void getUsers() throws Exception{
        // given
        User user = new User();
        user.setUsername("Username");
        user.setUserId(1L);
        user.setPassword("Password");
        user.setEmail("Mail@mail.com");
        user.setMoreInfo("IG: UserIG");
        user.setHighlightCounter(0);

        User anotherUser = new User();
        anotherUser.setUsername("anotherUsername");
        anotherUser.setUserId(2L);
        anotherUser.setPassword("anotherPassword");
        anotherUser.setEmail("anotherMail@mail.com");
        anotherUser.setMoreInfo("IG: anotherUserIG");
        anotherUser.setHighlightCounter(0);


        List<User> allUsers = Collections.singletonList(user);

        // this mocks the UserService -> we define above what the userService should
        // return when getUserByUserId() is called
        given(userService.getUserByUserId(1)).willReturn(user);

        // access the user with userId=1
        MockHttpServletRequestBuilder getRequest = get("/users/1").contentType(MediaType.APPLICATION_JSON);

        // then the returned user should have the same userName as user
        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(is(user.getUsername())));
    }


    @Test
    void createUser() throws Exception {
        // create a new user by doing a post request with the users credentials, expected to return 201 (isCreated)
        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"username\",\"email\":\"email\", \"password\":\"password\" }"))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void loginUser() throws Exception {
        // create a new user by doing a post request with the users credentials, expected to return 201 (isCreated)
        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"username\",\"email\":\"email\", \"password\":\"password\" }"))
                .andDo(print())
                .andExpect(status().isCreated());

        // then try to log in with the credentials
        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"username\", \"password\":\"password\" }"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void updateUser() throws Exception {
        // create a new user by doing a post request with the users credentials, expected to return 201 (isCreated)
        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"username\",\"email\":\"email\", \"password\":\"password\" }"))
                .andDo(print())
                .andExpect(status().isCreated());

        // then update user via put request, the header must include the userId
        this.mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userId", "1")
                        .content("{\"username\":\"username2\",\"email\":\"email2\", \"password\":\"password2\" }"))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    void deleteUser() throws Exception {
        // create a new user by doing a post request with the users credentials, expected to return 201 (isCreated)
        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"username\",\"email\":\"email\", \"password\":\"password\" }"))
                .andDo(print())
                .andExpect(status().isCreated());

        // then delete user via delete request, the header must include the userId
        this.mockMvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userId", "1"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }
}
/**
 given(userService.getUserByUserId(1)).willReturn(user);

 // when
 MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

 // then
 mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
 .andExpect(jsonPath("$[0].username", is(user.getUsername())))



 @Test
    public void createUser_validInput_userCreated() throws Exception {
        // given
        User user = new User();
        user.setUsername("testUsername");
        user.setToken("1");
        user.setUsername("Username");
        user.setPassword("Password");
        user.setEmail("Mail@mail.com");
        user.setMoreInfo("IG: UserIG");

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("testUsername");
        userPostDTO.setEmail("Mail@mail.com");

        given(userService.createUser(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        userPostDTOJSON = userPostDTO.t("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())));
    }

/**
  @Test
  public void createUser_validInput_userCreated() throws Exception {
    // given
    User user = new User();
    user.setUsername("testUsername");
    user.setToken("1");
    user.setUsername("Username");
    user.setPassword("Password");
    user.setEmail("Mail@mail.com");
    user.setMoreInfo("IG: UserIG");

    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("Test User");
    userPostDTO.setEmail("testUsername");

    given(userService.createUser(Mockito.any())).willReturn(user);

    // when/then -> do the request + validate the result
    MockHttpServletRequestBuilder postRequest = post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(userPostDTO));

    // then
    mockMvc.perform(postRequest)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(user.getId().intValue())))
        .andExpect(jsonPath("$.name", is(user.getName())))
        .andExpect(jsonPath("$.username", is(user.getUsername())))
        .andExpect(jsonPath("$.status", is(user.getStatus().toString())));
  }

  /**
   * Helper Method to convert userPostDTO into a JSON string such that the input
   * can be processed
   * Input will look like this: {"name": "Test User", "username": "testUsername"}
   * 
   * @param object
   * @return string
   */
/**
  private String asJsonString(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          String.format("The request body could not be created.%s", e.toString()));
    }
  }

} **/