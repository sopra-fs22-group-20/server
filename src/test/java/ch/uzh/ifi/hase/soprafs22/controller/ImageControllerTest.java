package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.Classification;
import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.ImageRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImagePostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ImagePutDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs22.service.ImageService;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ImageRepository imageRepository;


    @Test
    void getImage() throws Exception {
        // given
        Image image = new Image();
        image.setName("name");
        image.setStorageLink("storageLink");
        image.setLocation("location");
        image.setImageId(1L);

        given(imageService.getImageByImageId(1)).willReturn(image);

        // access the user with userId=1
        MockHttpServletRequestBuilder getRequest = get("/images/1").contentType(MediaType.APPLICATION_JSON);

        // then the returned user should have the same userName as user
        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(is(image.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.storageLink").value(is(image.getStorageLink())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value(is(image.getLocation())));
    }

    @Test
    void getAllImagesOfUser() throws Exception {
        // given
        User user = new User("username", "password", "email", "moreInfo");
        Image image = new Image();
        image.setName("name");
        image.setImageId(1L);
        image.setStorageLink("storageLink");
        image.setLocation("location");
        image.setOwner(user);

        List<Image> allImages = Collections.singletonList(image);

        given(imageService.getAllImagesOfUser(1l)).willReturn(allImages);

        // when
        MockHttpServletRequestBuilder getRequest = get("/images/all/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(image.getName())))
                .andExpect(jsonPath("$[0].storageLink", is(image.getStorageLink())))
                .andExpect(jsonPath("$[0].location", is(image.getLocation())));
    }

    @Test
    void getHighlightsFromCategory() throws Exception {
        User user = new User("username", "password", "email", "moreInfo");
        Category category = new Category("Fish");

        Image image1 = new Image();
        image1.setName("name1");
        image1.setImageId(1L);
        image1.setStorageLink("storageLink1");
        image1.setLocation("location");
        image1.setOwner(user);
        image1.setCategory(category);

        List<Image> highlightsList = Collections.singletonList(image1);

        given(imageService.getHighlights(Mockito.anyString())).willReturn(highlightsList);

        MockHttpServletRequestBuilder getRequest = get("/images/highlights/Fish").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(image1.getName())))
                .andExpect(jsonPath("$[0].storageLink", is(image1.getStorageLink())))
                .andExpect(jsonPath("$[0].location", is(image1.getLocation())));

    }

    @Test
    void getNonRatedImageFromCategory() throws Exception {
        User user = new User("username", "password", "email", "moreInfo");
        Category category = new Category("Fish");

        Image image1 = new Image();
        image1.setName("name1");
        image1.setImageId(1L);
        image1.setStorageLink("storageLink1");
        image1.setLocation("location");
        image1.setOwner(user);
        image1.setCategory(category);


        given(imageService.getRandomNonRatedImageFromCategory(Mockito.anyString(), Mockito.anyLong())).willReturn(image1);

        MockHttpServletRequestBuilder getRequest = get("/images/random/Fish").contentType(MediaType.APPLICATION_JSON);
        getRequest.header("userId", 1);


        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(image1.getName())))
                .andExpect(jsonPath("$.storageLink", is(image1.getStorageLink())))
                .andExpect(jsonPath("$.location", is(image1.getLocation())));
    }

    @Test
    void getNonRatedRandomImage() throws Exception {
        User user = new User("username", "password", "email", "moreInfo");
        Category category = new Category("Fish");

        Image image1 = new Image();
        image1.setName("name1");
        image1.setImageId(1L);
        image1.setStorageLink("storageLink1");
        image1.setLocation("location");
        image1.setOwner(user);
        image1.setCategory(category);


        given(imageService.getRandomNonRatedImage(Mockito.anyLong())).willReturn(image1);

        MockHttpServletRequestBuilder getRequest = get("/images/random/c").contentType(MediaType.APPLICATION_JSON);
        getRequest.header("userId", 1);


        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(image1.getName())))
                .andExpect(jsonPath("$.storageLink", is(image1.getStorageLink())))
                .andExpect(jsonPath("$.location", is(image1.getLocation())));
    }

    @Test
    void getRandomImage() throws Exception {
        User user = new User("username", "password", "email", "moreInfo");
        Category category = new Category("Fish");

        Image image1 = new Image();
        image1.setName("name1");
        image1.setImageId(1L);
        image1.setStorageLink("storageLink1");
        image1.setLocation("location");
        image1.setOwner(user);
        image1.setCategory(category);

        given(imageService.getRandomImage()).willReturn(image1);

        MockHttpServletRequestBuilder getRequest = get("/images/random").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(image1.getName())))
                .andExpect(jsonPath("$.storageLink", is(image1.getStorageLink())))
                .andExpect(jsonPath("$.location", is(image1.getLocation())));
    }

    @Test
    void checkIfRatedResponse() throws Exception {

        MockHttpServletRequestBuilder getRequest = get("/images/check/1").contentType(MediaType.APPLICATION_JSON);
        getRequest.header("userId", 1);

        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void createImage() throws Exception {

        User user = new User("username", "password", "email", "moreInfo");
        Category category = new Category("Fish");

        Image image1 = new Image();
        image1.setName("name1");
        image1.setImageId(1L);
        image1.setStorageLink("storageLink1");
        image1.setLocation("location");
        image1.setOwner(user);
        image1.setCategory(category);

        ImagePostDTO imagePostDTO = new ImagePostDTO();
        imagePostDTO.setName("name1");
        imagePostDTO.setStorageLink("storageLink1");
        imagePostDTO.setLocation("email@location.com");


        given(userService.getUserByUserId(Mockito.anyLong())).willReturn(user);
        given(imageService.createImage(image1, user)).willReturn(image1);

        MockHttpServletRequestBuilder postRequest = post("/images").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(imagePostDTO))
                .header("userId", 1);

        mockMvc.perform(postRequest).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    void updateImage() throws Exception {

        User user = new User("username", "password", "email", "moreInfo");
        Category category = new Category("Fish");

        Image image1 = new Image();
        image1.setName("oldName");
        image1.setImageId(1L);
        image1.setStorageLink("storageLink1");
        image1.setLocation("location");
        image1.setOwner(user);
        image1.setCategory(category);

        Image image2 = new Image();
        image2.setName("newName");
        image2.setImageId(1L);
        image2.setStorageLink("storageLink1");
        image2.setLocation("location");
        image2.setOwner(user);
        image2.setCategory(category);

        ImagePutDTO imagePutDTO = new ImagePutDTO();
        imagePutDTO.setName("newName");

        given(imageService.getImageByImageId(Mockito.anyLong())).willReturn(image1);

        given(imageService.getImageByImageId(Mockito.anyLong())).willReturn(image2);

        MockHttpServletRequestBuilder putRequest = put("/images/1").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(imagePutDTO))
                .header("userId", 1);

        mockMvc.perform(putRequest).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void rateImageResponse() throws Exception {

        ImagePutDTO imagePutDTO = new ImagePutDTO();
        imagePutDTO.setImageId(1L);
        imagePutDTO.setRating(5);

        MockHttpServletRequestBuilder getRequest = put("/rate").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(imagePutDTO))
                .header("userId", 1);

        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void boost() throws Exception {
        //Setup
        ImagePutDTO imagePutDTO = new ImagePutDTO();
        imagePutDTO.setImageId(1L);

        User user = new User("username", "password", "email", "moreInfo");
        user.setUserId(1L);
        user.setTrophies(20);

        //Mock
        given(userRepository.findByUserId(Mockito.anyLong())).willReturn(user);

        //apply
        MockHttpServletRequestBuilder getRequest = put("/images/boost").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(imagePutDTO))
                .header("userId", 1);

        //assertions
        mockMvc.perform(getRequest).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void updateClassification() throws Exception {
        //Setup
        User user = new User("username", "password", "email", "moreInfo");
        Category category = new Category("Fish");

        Image image = new Image();
        image.setName("oldName");
        image.setImageId(1L);
        image.setStorageLink("storageLink1");
        image.setLocation("location");
        image.setOwner(user);
        image.setCategory(category);
        image.setClassification(Classification.A);

        ImagePutDTO imagePutDTO = new ImagePutDTO();
        imagePutDTO.setImageId(1L);
        imagePutDTO.setClassification(Classification.A);

        //Mock
        given(imageService.updateClassification(Mockito.any())).willReturn(image);

        //apply
        MockHttpServletRequestBuilder putRequest = put("/classification").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(imagePutDTO))
                .header("userId", 1);

        //assertions
        mockMvc.perform(putRequest).andDo(print()).andExpect(status().isNoContent())
                .andExpect(jsonPath("$.classification", is("A")));
    }

    @Test
    void deleteImage() throws Exception {
        // create a new user by doing a post request with the users credentials, expected to return 201 (isCreated)
        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"username\",\"email\":\"email\", \"password\":\"password\" }"))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(post("/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userId", 1L)
                        .content("{\"name\":\"name\",\"location\":\"location\", \"storageLink\":\"storageLink\", \"category\":\"Fish\"}"))
                .andDo(print())
                .andExpect(status().isCreated());

        // then delete user via delete request, the header must include the userId
        this.mockMvc.perform(delete("/images/1")
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