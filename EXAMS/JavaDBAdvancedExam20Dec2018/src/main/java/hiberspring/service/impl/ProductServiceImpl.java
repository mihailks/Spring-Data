package hiberspring.service.impl;

import hiberspring.domain.dtos.products.ProductSeedRootDTO;
import hiberspring.domain.entities.Product;
import hiberspring.repository.ProductRepository;
import hiberspring.service.BranchService;
import hiberspring.service.ProductService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ProductServiceImpl implements ProductService {
    public static final String PRODUCTS_FILE_NAME = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBAdvancedExam20Dec2018\\src\\main\\resources\\files\\products.xml";
    private final ProductRepository productRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final BranchService branchService;

    public ProductServiceImpl(ProductRepository productRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, BranchService branchService) {
        this.productRepository = productRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.branchService = branchService;
    }

    @Override
    public Boolean productsAreImported() {
        return productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return Files.readString(Path.of(PRODUCTS_FILE_NAME));
    }

    @Override
    public String importProducts() throws JAXBException, FileNotFoundException {

        StringBuilder stringBuilder = new StringBuilder();

        xmlParser.parseXml(ProductSeedRootDTO.class, PRODUCTS_FILE_NAME)
                .getProducts()
                .stream()
                .filter(productSeedDTO -> {
                    boolean isValid = validationUtil.isValid(productSeedDTO);

                    stringBuilder
                            .append(isValid ? String.format("Successfully imported Product %s.", productSeedDTO.getName())
                                    : "Invalid data!")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(productSeedDTO -> {
                    Product product = modelMapper.map(productSeedDTO, Product.class);
                    product.setBranch(branchService.findByName(productSeedDTO.getBranch()));
                    return product;
                })
                .forEach(productRepository::save);

        return stringBuilder.toString().trim();
    }
}
