package bg.softuni.exercisexmlprocessing;

import bg.softuni.exercisexmlprocessing.model.DTO.CategoriesSeedRootDTO;
import bg.softuni.exercisexmlprocessing.model.DTO.ProductSeedRootDTO;
import bg.softuni.exercisexmlprocessing.model.DTO.UserSeedRootDTO;
import bg.softuni.exercisexmlprocessing.servise.CategoryService;
import bg.softuni.exercisexmlprocessing.servise.ProductService;
import bg.softuni.exercisexmlprocessing.servise.UserService;
import bg.softuni.exercisexmlprocessing.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;


@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String RESOURCE_FILE_PATH = "src/main/resources/files/";
    private static final String CATEGORY_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_NAME = "users.xml";
    private static final String PRODUCTS_FILE_NAME = "products.xml";

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
    }

    private void seedData() throws FileNotFoundException, JAXBException {
        if (categoryService.getEntityCount()==0){
            CategoriesSeedRootDTO categoriesSeedRootDTO = xmlParser.fromFile(RESOURCE_FILE_PATH + CATEGORY_FILE_NAME,
                    CategoriesSeedRootDTO.class);

            categoryService.seedCategories(categoriesSeedRootDTO.getCategories());
        }

        if (userService.getEntityCount()==0){

            UserSeedRootDTO userSeedRootDTO = xmlParser.fromFile(RESOURCE_FILE_PATH + USERS_FILE_NAME,
                    UserSeedRootDTO.class);

            userService.seedUsers(userSeedRootDTO.getUsers());
        }

        if (productService.getEntityCount()==0){

            ProductSeedRootDTO productSeedRootDTO = xmlParser.fromFile(RESOURCE_FILE_PATH + PRODUCTS_FILE_NAME,
                    ProductSeedRootDTO.class);


            productService.seedService(productSeedRootDTO.getProducts());
        }





    }
}
