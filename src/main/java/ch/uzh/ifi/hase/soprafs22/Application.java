package ch.uzh.ifi.hase.soprafs22;

import ch.uzh.ifi.hase.soprafs22.constant.Current_Date;
import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.CategoryRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@SpringBootApplication
public class Application {
    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;


    public Application(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String helloWorld() {
        return "The application is running.";
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }

    @Bean
    public void setupCategory() {
        Category all[] = new Category[6];
        all[0] = new Category("Fish");
        all[1] = new Category("Car");
        all[2] = new Category("Cat");
        all[3] = new Category("Dog");
        all[4] = new Category("Motorcycle");
        all[5] = new Category("Random");


        for (Category category : all) {
            Category y = this.categoryRepository.save(category);
            this.categoryRepository.flush();
        }
    }

    @Bean
    public void setupUser() {
        User user = new User("testUser", "1234", "emailxxx@gmail.com", "Hi");

        user.setTrophies(999999);
        user.setToken("a6s5d4f6asdf");
        user.setCreationDate(Current_Date.getSQLDate());

        this.userRepository.saveAndFlush(user);
    }
}
