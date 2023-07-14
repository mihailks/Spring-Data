package bg.softuni.exercisejsonprocessing.servise;

import bg.softuni.exercisejsonprocessing.model.DTO.ProductNameAndPriceDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void seedProducts() throws IOException;

    List<ProductNameAndPriceDTO> findAllProductInRangeOrderByPrice(BigDecimal lower, BigDecimal upper);
}
