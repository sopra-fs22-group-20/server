package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CategoryPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.CategoryService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;


    @Test
    void getCategories() throws Exception {
        //given
        Category testCategory = new Category();
        testCategory.setName("Fish");

        List<Category> allCategories = Collections.singletonList(testCategory);

        //Mock
        given(categoryService.getCategories()).willReturn(allCategories);

        //when
        MockHttpServletRequestBuilder getRequest = get("/categories").contentType(MediaType.APPLICATION_JSON);

        //then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(testCategory.getName())));
    }

    @Test
    void createCategory() throws Exception {
        //given
        Category testCategory = new Category();
        testCategory.setName("Aero");

        //setup
        CategoryPostDTO categoryPostDTO = new CategoryPostDTO();
        categoryPostDTO.setName("Aero");

        //Mock
        given(categoryService.createCategory(Mockito.any())).willReturn(testCategory);

        //when
        MockHttpServletRequestBuilder postRequest = post("/category").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(categoryPostDTO));

        //then
        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(testCategory.getName())));
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