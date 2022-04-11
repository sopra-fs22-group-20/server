package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CategoryGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.CategoryService;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
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
    public CategoryGetDTO getCategory() {

       Category category = null; //TODO test

        return DTOMapper.INSTANCE.convertEntityToCategoryGetDTO(category);
    }


}
