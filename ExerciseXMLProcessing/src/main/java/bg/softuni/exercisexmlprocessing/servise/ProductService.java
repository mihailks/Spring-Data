package bg.softuni.exercisexmlprocessing.servise;

import bg.softuni.exercisexmlprocessing.model.DTO.ProductSeedDTO;
import bg.softuni.exercisexmlprocessing.model.DTO.ProductViewRootDTO;

import java.util.List;

public interface ProductService {
    long getEntityCount();

    void seedService(List<ProductSeedDTO> products);

    ProductViewRootDTO findProductsInRangeWithoutBuyer();
}
