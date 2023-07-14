package bg.softuni.exercisexmlprocessing.repository;

import bg.softuni.exercisexmlprocessing.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

}
