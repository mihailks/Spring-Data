package bg.softuni.exercisespringdataintro.repository;

import bg.softuni.exercisespringdataintro.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
