package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CategoryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category testCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        testCategory = new Category();
        testCategory.setName("testFish");
    }

    @Test
    void findByName() {
        //setup
        entityManager.persist(testCategory);
        entityManager.flush();

        //when
        Category found = categoryRepository.findByName(testCategory.getName());

        //assertions
        assertEquals(found.getName(), testCategory.getName());

    }
}