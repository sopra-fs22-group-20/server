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

/**
 * Category Controller
 * This class is responsible for handling all REST request that are related to
 * the category.
 * The controller will receive the request and delegate the execution to the
 * CategoryService and finally return the result.
 */
@RestController
public class CategoryController {

    private final CategoryService categoryService;

   CategoryController(CategoryService categoryService) {
       this.categoryService = categoryService;
   }

    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CategoryGetDTO> getCategory() {
        /*CategoryGetDTO[] allCategories = {
                DTOMapper.INSTANCE.convertEntityToCategoryGetDTO(new Category("Test1")),
                DTOMapper.INSTANCE.convertEntityToCategoryGetDTO(new Category("Test2")),
                DTOMapper.INSTANCE.convertEntityToCategoryGetDTO(new Category("Test3")),
                DTOMapper.INSTANCE.convertEntityToCategoryGetDTO(new Category("Test4")),
                DTOMapper.INSTANCE.convertEntityToCategoryGetDTO(new Category("Test5"))
        };
        return allCategories;*/

        // fetch all categories in the internal representation
        List<Category> categories = null; // = categoryService.getCategories();
        categories.add(new Category("Test1"));
        categories.add(new Category("Test2"));
        categories.add(new Category("Test3"));
        categories.add(new Category("Test4"));
        categories.add(new Category("Test5"));
        List<CategoryGetDTO> categoryGetDTOs = new ArrayList<>();

        // convert each category to the API representation
        for (Category category : categories) {
            categoryGetDTOs.add(DTOMapper.INSTANCE.convertEntityToCategoryGetDTO(category));
        }
        return categoryGetDTOs;
    }

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CategoryGetDTO createCategory(@RequestBody CategoryPostDTO categoryPostDTO) {
        // convert API user to internal representation
        Category categoryInput = DTOMapper.INSTANCE.convertCategoryPostDTOtoEntity(categoryPostDTO);

        // create category
        Category createdCategory = categoryService.addCategory(categoryInput.getCategory());

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToCategoryGetDTO(createdCategory);
    }


}
