package bg.softuni.exercisexmlprocessing.servise.impl;

import bg.softuni.exercisexmlprocessing.model.DTO.ProductSeedDTO;
import bg.softuni.exercisexmlprocessing.model.DTO.ProductViewRootDTO;
import bg.softuni.exercisexmlprocessing.model.DTO.ProductWithSellerDTO;
import bg.softuni.exercisexmlprocessing.model.entity.Product;
import bg.softuni.exercisexmlprocessing.repository.ProductsRepository;
import bg.softuni.exercisexmlprocessing.servise.CategoryService;
import bg.softuni.exercisexmlprocessing.servise.ProductService;
import bg.softuni.exercisexmlprocessing.servise.UserService;
import bg.softuni.exercisexmlprocessing.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository productsRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductsRepository productsRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, CategoryService categoryService) {
        this.productsRepository = productsRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public long getEntityCount() {
        return productsRepository.count();
    }

    @Override
    public void seedService(List<ProductSeedDTO> products) {
        products
                .stream()
                .filter(validationUtil::isValid)
                .map(productSeedDTO -> {
                    Product product = modelMapper.map(productSeedDTO, Product.class);
                    product.setSeller(userService.getRandomUser());
                    if (product.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0) {
                        product.setBuyer(userService.getRandomUser());
                    }
                    product.setCategories(categoryService.getRandomCategories());

                    return product;
                })
                .forEach(productsRepository::save);
    }

    @Override
    public ProductViewRootDTO findProductsInRangeWithoutBuyer() {
        ProductViewRootDTO productRootDTO = new ProductViewRootDTO();

        productRootDTO
                .setProducts(productsRepository
                        .findAllByPriceBetweenAndBuyerIsNull(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L))
                        .stream()
                        .map(product -> {
                            ProductWithSellerDTO productWithSellerDTO = modelMapper.map(product, ProductWithSellerDTO.class);
                            productWithSellerDTO.setSeller(String.format("%s %s",
                                    product.getSeller().getFirstName() == null ? "" : product.getSeller().getFirstName(),
                                    product.getSeller().getLastName()));
                            return productWithSellerDTO;
                        })
                        .collect(Collectors.toList()));


        return productRootDTO;
    }
}
