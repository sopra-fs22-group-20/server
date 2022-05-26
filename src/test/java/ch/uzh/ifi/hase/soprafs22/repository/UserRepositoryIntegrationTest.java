package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    //Don't change order because ID are auto generated Like this

    @Test
    public void findByName_success() {
        // given
        User user = new User();
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setPassword("1234");
        user.setToken("1");


        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByUsername(user.getUsername());

        // then
        assertNotNull(found.getUserId());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getEmail(), user.getEmail());
        assertEquals(found.getPassword(), user.getPassword());
        assertEquals(found.getToken(), user.getToken());
    }

    @Test
    void findByEmail() {
        // given
        User user = new User();
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setPassword("1234");
        user.setToken("2");

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByEmail(user.getEmail());

        // then
        assertNotNull(found.getUserId());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getEmail(), user.getEmail());
        assertEquals(found.getPassword(), user.getPassword());
        assertEquals(found.getToken(), user.getToken());
    }



    @Test
    void findByUserId() {
        // given
        User user = new User();
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setPassword("1234");
        user.setToken("3");

        entityManager.persist(user);
        entityManager.flush();

        //Get userId
        Long id = userRepository.findByUsername("username").getUserId();
        // when
        User found = userRepository.findByUserId(id);

        // then
        assertNotNull(found.getUserId());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getEmail(), user.getEmail());
        assertEquals(found.getPassword(), user.getPassword());
        assertEquals(found.getToken(), user.getToken());
    }

    @Test
    void deleteUserByUserId() {
        // given
        User user = new User();
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setPassword("1234");
        user.setToken("4");

        entityManager.persist(user);
        entityManager.flush();

        //Get userId
        Long id = userRepository.findByUsername("username").getUserId();

        // when
        userRepository.deleteUserByUserId(id);

        User found = userRepository.findByUserId(id);

        // then
        assertNull(found);
    }
}
