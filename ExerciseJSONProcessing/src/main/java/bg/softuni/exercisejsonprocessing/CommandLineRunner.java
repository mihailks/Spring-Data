package bg.softuni.exercisejsonprocessing;

import bg.softuni.exercisejsonprocessing.model.DTO.CategoryProductCountDTO;
import bg.softuni.exercisejsonprocessing.model.DTO.ProductNameAndPriceDTO;
import bg.softuni.exercisejsonprocessing.model.DTO.Q4.UserCountDTO;
import bg.softuni.exercisejsonprocessing.model.DTO.UserSoldDTO;
import bg.softuni.exercisejsonprocessing.servise.CategoryService;
import bg.softuni.exercisejsonprocessing.servise.ProductService;
import bg.softuni.exercisejsonprocessing.servise.UserService;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
    private static final String OUTPUT_FILES_PATH = "src/main/resources/files/out/";
    public static final String PRODUCT_IN_RANGE_FILE_NAME = "products-in-range.json";
    public static final String PRODUCT_SOLD_FILE_NAME = "products-sold.json";
    public static final String PRODUCT_COUNT_FILE_NAME = "products-count.json";
    public static final String USERS_PRODUCTS_FILE_NAME = "users-products.json";
    private CategoryService categoryService;
    private UserService userService;
    private ProductService productService;
    private final Gson gson;

    public CommandLineRunner(CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        seedData();

        System.out.println("Enter number");
        int task = Integer.parseInt(scanner.nextLine());

        switch (task) {
            case 1 -> productsInRange();
            case 2 -> soldProducts();
            case 3 -> categoriesByProductCount();
            case 4 -> UsersAndProducts();
        }

    }

    private void UsersAndProducts() {
        List<UserCountDTO> userSoldDTOs =
                userService.countUserByProductSold();
    }

    private void categoriesByProductCount() throws IOException {
        List<CategoryProductCountDTO> categoryProductCountDTOs =
                categoryService.countCategories();
        String content = gson.toJson(categoryProductCountDTOs);
        writeToFile(OUTPUT_FILES_PATH + PRODUCT_COUNT_FILE_NAME, content);
    }


    private void soldProducts() throws IOException {
        List<UserSoldDTO> userSoldDTOS = userService.findAllUsersWithMoreThenProductsSold();
        String content = gson.toJson(userSoldDTOS);
        writeToFile(OUTPUT_FILES_PATH + PRODUCT_SOLD_FILE_NAME, content);

    }

    private void productsInRange() throws IOException {
        List<ProductNameAndPriceDTO> productDTOs = productService
                .findAllProductInRangeOrderByPrice(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L));

        String content = gson.toJson(productDTOs);

        writeToFile(OUTPUT_FILES_PATH + PRODUCT_IN_RANGE_FILE_NAME, content);

    }

    private void writeToFile(String filePath, String content) throws IOException {
        Files.write(Path.of(filePath), Collections.singleton(content));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }


}
