package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CategoryGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CategoryPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Returns all existing categories
     * Get Nr. 6
     */
    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CategoryGetDTO> getCategory() {
        // fetch all categories in the internal representation
        List<Category> categories = categoryService.getCategories();
        List<CategoryGetDTO> categoryGetDTOs = new ArrayList<>();

        // convert each category to the API representation
        for (Category category : categories) {
            categoryGetDTOs.add(DTOMapper.INSTANCE.convertEntityToCategoryGetDTO(category));
        }
        return categoryGetDTOs;
    }

    /**
     * Create a new category
     * Post Nr. 4
     */
    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CategoryGetDTO createCategory(@RequestBody CategoryPostDTO categoryPostDTO) {
        // convert API user to internal representation
        Category categoryInput = DTOMapper.INSTANCE.convertCategoryPostDTOtoEntity(categoryPostDTO);

        // create category
        Category createdCategory = categoryService.createCategory(categoryInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToCategoryGetDTO(createdCategory);
    }
}
