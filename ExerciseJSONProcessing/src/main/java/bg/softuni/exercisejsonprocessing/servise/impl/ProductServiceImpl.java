package bg.softuni.exercisejsonprocessing.servise.impl;

import bg.softuni.exercisejsonprocessing.model.DTO.ProductNameAndPriceDTO;
import bg.softuni.exercisejsonprocessing.model.DTO.ProductSeedDTO;
import bg.softuni.exercisejsonprocessing.model.entity.Product;
import bg.softuni.exercisejsonprocessing.repository.ProductsRepository;
import bg.softuni.exercisejsonprocessing.servise.CategoryService;
import bg.softuni.exercisejsonprocessing.servise.ProductService;
import bg.softuni.exercisejsonprocessing.servise.UserService;
import bg.softuni.exercisejsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.exercisejsonprocessing.constanst.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class ProductServiceImpl implements ProductService {
    public static final String PRODUCTS_FILE_NAME = "products.json";

    private final ProductsRepository productsRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductsRepository productsRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, UserService userService, CategoryService categoryService) {
        this.productsRepository = productsRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedProducts() throws IOException {
        if (productsRepository.count() > 0) {
            return;
        }

        //read from json
        String fileContent = Files.
                readString(Path.of(RESOURCES_FILE_PATH + PRODUCTS_FILE_NAME));

        //parse from string to DTO
        ProductSeedDTO[] productSeedDTOs = gson.fromJson(fileContent, ProductSeedDTO[].class);


        Arrays.stream(productSeedDTOs)
                .filter(validationUtil::isValid)
                .map(productSeedDTO -> {
                    Product product = modelMapper.map(productSeedDTO, Product.class);
                    product.setSeller(userService.findRandomUser());
                    if (product.getPrice().compareTo(BigDecimal.valueOf(900L)) > 0) {
                        product.setBuyer(userService.findRandomUser());
                    }

                    product.setCategories(categoryService.findRandomCategories());

                    return product;

                })
                .forEach(productsRepository::save);


    }

    @Override
    public List<ProductNameAndPriceDTO> findAllProductInRangeOrderByPrice(BigDecimal lower, BigDecimal upper) {
        return productsRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(lower, upper)
                .stream()
                .map(product -> {
                    ProductNameAndPriceDTO productNameAndPriceDTO = modelMapper
                            .map(product, ProductNameAndPriceDTO.class);

                    productNameAndPriceDTO.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));

                    return productNameAndPriceDTO;
                })
                .collect(Collectors.toList());
    }
}












