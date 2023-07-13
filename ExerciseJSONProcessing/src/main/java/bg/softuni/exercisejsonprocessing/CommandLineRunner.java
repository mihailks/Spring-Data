package bg.softuni.exercisejsonprocessing;

import bg.softuni.exercisejsonprocessing.servise.CategoryService;
import bg.softuni.exercisejsonprocessing.servise.ProductService;
import bg.softuni.exercisejsonprocessing.servise.UserService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private CategoryService categoryService;
    private UserService userService;
    private ProductService productService;

    public CommandLineRunner(CategoryService categoryService, UserService userService, ProductService productService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
            seedData();
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }
}
