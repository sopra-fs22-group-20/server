package ch.uzh.ifi.hase.soprafs22;

import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.repository.CategoryRepository;
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


    public Application(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
    public void setUpCategory() {
        Category all [] = new Category[5];
        all[0] = new Category("Fish");
        all[1] = new Category("Car");
        all[2] = new Category("Cat");
        all[3] = new Category("Dog");
        all[4] = new Category("Motorcycle");

        for (Category category : all) {
            Category y = this.categoryRepository.save(category);
            this.categoryRepository.flush();
        }
    }
}
