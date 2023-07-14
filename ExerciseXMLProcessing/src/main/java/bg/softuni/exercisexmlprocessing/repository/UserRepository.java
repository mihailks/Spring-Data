package bg.softuni.exercisexmlprocessing.repository;

import bg.softuni.exercisexmlprocessing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
