package bg.softuni.exercisexmlprocessing;

import bg.softuni.exercisexmlprocessing.model.DTO.CategoriesSeedRootDTO;
import bg.softuni.exercisexmlprocessing.model.DTO.ProductSeedRootDTO;
import bg.softuni.exercisexmlprocessing.model.DTO.ProductViewRootDTO;
import bg.softuni.exercisexmlprocessing.model.DTO.Q2.UsersViewRootDTO;
import bg.softuni.exercisexmlprocessing.model.DTO.UserSeedRootDTO;
import bg.softuni.exercisexmlprocessing.servise.CategoryService;
import bg.softuni.exercisexmlprocessing.servise.ProductService;
import bg.softuni.exercisexmlprocessing.servise.UserService;
import bg.softuni.exercisexmlprocessing.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Scanner;


@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String RESOURCE_FILE_PATH = "src/main/resources/files/";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/files/out/";
    private static final String CATEGORY_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_NAME = "users.xml";
    private static final String PRODUCTS_FILE_NAME = "products.xml";

    private static final String PRODUCTS_IN_RANGE_FILE_NAME = "ProductsInRange.xml";
    private static final String Sold_Products_FILE_NAME = "SoldProducts.xml";

    private final XmlParser xmlParser;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;

    public CommandLineRunnerImpl(XmlParser xmlParser, CategoryService categoryService, UserService userService, ProductService productService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
    }


    @Override
    public void run(String... args) throws Exception {
        seedData();
        System.out.println("ENTER NUMBER");
        Scanner scanner = new Scanner(System.in);
        int input = Integer.parseInt(scanner.nextLine());
        switch (input) {
            case 1 -> productsInRange();
            case 2 -> soldProducts();
        }
    }

    private void soldProducts() throws JAXBException {
        UsersViewRootDTO usersViewRootDTO = userService.findUsersWithMoreThenOneSoldProduct();

        xmlParser.writeToFile(OUTPUT_FILE_PATH + Sold_Products_FILE_NAME,usersViewRootDTO);
    }

    private void productsInRange() throws JAXBException {

        ProductViewRootDTO productRootDTO = productService
                .findProductsInRangeWithoutBuyer();

        xmlParser.writeToFile(OUTPUT_FILE_PATH + PRODUCTS_IN_RANGE_FILE_NAME, productRootDTO);

    }

    private void seedData() throws FileNotFoundException, JAXBException {
        if (categoryService.getEntityCount() == 0) {
            CategoriesSeedRootDTO categoriesSeedRootDTO = xmlParser.fromFile(RESOURCE_FILE_PATH + CATEGORY_FILE_NAME,
                    CategoriesSeedRootDTO.class);

            categoryService.seedCategories(categoriesSeedRootDTO.getCategories());
        }

        if (userService.getEntityCount() == 0) {

            UserSeedRootDTO userSeedRootDTO = xmlParser.fromFile(RESOURCE_FILE_PATH + USERS_FILE_NAME,
                    UserSeedRootDTO.class);

            userService.seedUsers(userSeedRootDTO.getUsers());
        }

        if (productService.getEntityCount() == 0) {

            ProductSeedRootDTO productSeedRootDTO = xmlParser.fromFile(RESOURCE_FILE_PATH + PRODUCTS_FILE_NAME,
                    ProductSeedRootDTO.class);


            productService.seedService(productSeedRootDTO.getProducts());
        }


    }
}
