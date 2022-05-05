package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.CategoryRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * Category Service
 * This class is the "worker" and responsible for all functionality related to
 * the category
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(@Qualifier("categoryRepository") CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category newCategory) {
        checkIfCategoryExists(newCategory);

        newCategory = categoryRepository.save(newCategory);
        categoryRepository.flush();

        return newCategory;
    }

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    public Category getCategoryByCategory(String category){
        Category tempCategory = categoryRepository.findByCategory(category);
        return tempCategory;
    }

    public void checkIfCategoryExists(Category category) {
        Category tempCategory = categoryRepository.findByCategory(category.getCategory());

        if (tempCategory != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("This category already exists!"));
        }
    }
}