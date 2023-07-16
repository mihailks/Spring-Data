package bg.softuni.exercisejsonprocessing.repository;

import bg.softuni.exercisejsonprocessing.model.DTO.CategoryProductCountDTO;
import bg.softuni.exercisejsonprocessing.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    SELECT c.name, count(*) from categories as c
//    join products_categories as pc on c.id = pc.categories_id
//    GROUP BY c.name;

//    @Query("SELECT c.name, count (p.id), avg(p.price), sum(p.price)" +
//            "from Product  p " +
//            "JOIN p.categories c " +
//            "group by c.id " +
//            "order by count (p.id) DESC")

//    @Query("SELECT c.name, count (p.id), avg(p.price), sum(p.price)" +
//            "from Product p " +
//            "JOIN p.categories c " +
//            "group by c.id " +
//            "order by count (p.id) DESC")


    @Query("SELECT new bg.softuni.exercisejsonprocessing.model.DTO.CategoryProductCountDTO" +
            "(c.name, count (p.id), avg(p.price), sum(p.price))" +
            "from Product  p " +
            "JOIN p.categories c " +
            "group by c.id " +
            "order by count (p.id) DESC")
            List<CategoryProductCountDTO>findAllByNumberOfProducts();

//    @Query(value = "SELECT c.name, count(*) from categories as c " +
//            "join products_categories as pc on c.id = pc.categories_id " +
//            "GROUP BY c.name", nativeQuery = true)
//    List<Category> findAllByNumberOfProducts();


}
