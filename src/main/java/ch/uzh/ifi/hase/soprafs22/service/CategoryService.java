package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

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

        log.debug("Created Information for Category: {}", newCategory);
        return newCategory;
    }

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    public void checkIfCategoryExists(Category category) {
        Category tempCategory = categoryRepository.findByName(category.getName());

        if (!Objects.equals(tempCategory, null)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("This category already exists!"));
        }
    }
}