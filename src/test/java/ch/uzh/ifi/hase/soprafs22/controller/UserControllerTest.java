package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsers() throws Exception {
        //given
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

        //when
        MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

        //then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].moreInfo", is(user.getMoreInfo())))
                .andExpect(jsonPath("$[0].highlightCounter", is(user.getHighlightCounter())))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())));

    }

    @Test
    void getUsers() throws Exception {
        // given
        User user = new User();
        user.setUsername("Username");
        user.setUserId(1L);
        user.setPassword("Password");
        user.setEmail("Mail@mail.com");
        user.setMoreInfo("IG: UserIG");
        user.setHighlightCounter(0);

        given(userService.getUserByUserId(1L)).willReturn(user);
        // when
        MockHttpServletRequestBuilder getRequest = get("/users/1").contentType(MediaType.APPLICATION_JSON);
        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.moreInfo", is(user.getMoreInfo())))
                .andExpect(jsonPath("$.highlightCounter", is(user.getHighlightCounter())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    void createUser() throws Exception {
        // given
        User user = new User();
        user.setUsername("Username");
        user.setPassword("1234");
        user.setEmail("email@gmail.com");
        user.setHighlightCounter(0);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("Username");
        userPostDTO.setPassword("1234");
        userPostDTO.setEmail("email@gmail.com");

        // when/then -> do the request + validate the result
        given(userService.createUser(Mockito.any())).willReturn(user);

        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));
        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.highlightCounter", is(user.getHighlightCounter())))
                .andExpect(jsonPath("$.email", is((user.getEmail()))));
    }

    @Test
    void createUserResponse() throws Exception {
        // create a new user by doing a post request with the users credentials, expected to return 201 (isCreated)
        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"username\",\"email\":\"email\", \"password\":\"password\" }"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void loginUserSuccess() throws Exception {
        // given
        User user = new User();
        user.setUsername("Username");
        user.setPassword("1234");
        user.setEmail("email@gmail.com");
        user.setHighlightCounter(0);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("Username");
        userPostDTO.setPassword("1234");

        given(userService.createUser(Mockito.any())).willReturn(user);

        MockHttpServletRequestBuilder postRequest = post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isOk());

    }

    @Test
    void loginUserSuccessResponse() throws Exception {
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
    void updateUserResponse() throws Exception {
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
    void updateTrophies() throws Exception {
        // given
        User user = new User();
        user.setUserId(1L);
        user.setTrophies(10);
        user.setUsername("Username");
        user.setPassword("1234");
        user.setEmail("email@gmail.com");
        user.setHighlightCounter(0);


        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setUserId(1L);
        userPutDTO.setTrophies(10);

        given(userService.updateTrophies(Mockito.any())).willReturn(user);

        MockHttpServletRequestBuilder putRequest = put("/users/trophies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.trophies", is(user.getTrophies())));

    }

    @Test
    void deleteUserResponse() throws Exception {
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


    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }

}