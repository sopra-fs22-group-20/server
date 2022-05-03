package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImagePostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.ImageService;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;
    @MockBean
    private ImagePostDTO imagePostDTO;

    @Test
    void getImage() throws Exception{
        // given
        Image image = new Image();
        image.setName("Username");
        image.setImageId(1L);
        image.setStorageLink("storageLink");
        image.setLocation("location");


        List<Image> allUsers = Collections.singletonList(image);

        // this mocks the UserService -> we define above what the userService should
        // return when getUserByUserId() is called
        given(imageService.getImageByImageId(1)).willReturn(image);

        // access the user with userId=1
        MockHttpServletRequestBuilder getRequest = get("/images/1").contentType(MediaType.APPLICATION_JSON);

        // then the returned user should have the same userName as user
        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(is(image.getName())));
    }

    @Test
    void getAllImagesOfUser() throws Exception {
        // given
        User user = new User("username","password", "email", "moreInfo");
        Image image = new Image();
        image.setName("Username");
        image.setImageId(1L);
        image.setStorageLink("storageLink");
        image.setLocation("location");
        image.setOwner(user);

        List<Image> allImages = Collections.singletonList(image);

        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(imageService.getAllImagesOfUser(1l)).willReturn(allImages);

        // when
        MockHttpServletRequestBuilder getRequest = get("/images").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void createImageNoUser() throws Exception {
        // create a new user by doing a post request with the image credentials without a User, expected to return 404
        this.mockMvc.perform(post("/images")
                        .header("UserId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"location\":\"location\",\"storageLink\":\"storageLink\", \"name\":\"name\" }"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    /**
    @Test
    void updateImage() throws Exception {

        // given
        Image image = new Image();
        image.setName("Username");
        image.setImageId(1L);
        image.setStorageLink("storageLink");
        image.setLocation("location");

        Image updateImage = new Image();
        updateImage.setName("UpdateUsername");
        updateImage.setStorageLink("UpdateStorageLink");
        updateImage.setLocation("UpdateLocation");


        List<Image> allUsers = Collections.singletonList(image);

        // this mocks the UserService -> we define above what the userService should
        // return when getUserByUserId() is called
        given(imageService.getImageByImageId(1)).willReturn(image);

        // access the user with userId=1
        MockHttpServletRequestBuilder putRequest = put("/images/1").contentType(MediaType.APPLICATION_JSON);

        // then the returned user should have the same userName as user
        mockMvc.perform(putRequest).andDo(print()).andExpect(status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(is(image.getName())));
    }

    @Test
    void deleteImage() {
    }
    **/
}