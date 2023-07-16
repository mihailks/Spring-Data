package bg.softuni.exercisexmlprocessing.repository;

import bg.softuni.exercisexmlprocessing.model.DTO.ProductViewRootDTO;
import bg.softuni.exercisexmlprocessing.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceBetweenAndBuyerIsNull(BigDecimal lower, BigDecimal upper);

}
