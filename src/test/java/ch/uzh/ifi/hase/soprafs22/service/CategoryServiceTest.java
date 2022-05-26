package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        testCategory = new Category();
        testCategory.setName("testFish");
    }

    @Test
    void createCategory() {
        //Mock
        given(categoryRepository.save(Mockito.any())).willReturn(testCategory);

        //apply
        Category category = categoryService.createCategory(testCategory);
        //then
        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any());

        //assertion
        assertEquals(testCategory.getName(), category.getName());

    }

    @Test
    void getCategories() {
        //setup
        List<Category> allCategories = Collections.singletonList(testCategory);

        //Mock
        given(categoryRepository.findAll()).willReturn(allCategories);
        //apply
        List<Category> foundCategories = categoryService.getCategories();

        //assertion
        assertEquals(foundCategories.get(0).getName(), testCategory.getName());
    }

    @Test
    void checkIfCategoryExists() {
        /**
         //Mock
         given(categoryRepository.findByName(Mockito.anyString())).willReturn(testCategory);

         //assertion
         assertThrows(ResponseStatusException.class, () -> categoryService.checkIfCategoryExists(Mockito.any()));
         */
    }
}