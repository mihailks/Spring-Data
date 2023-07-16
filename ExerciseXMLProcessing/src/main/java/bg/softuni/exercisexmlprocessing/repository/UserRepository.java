package bg.softuni.exercisexmlprocessing.repository;

import bg.softuni.exercisexmlprocessing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    List<User> findAllBySoldProductsIsNotNullOrderByLastNameAscFirstName();


    @Query("select u from User as u where " +
            "(select count(p) from Product as p " +
            "where p.seller.id = u.id and p.buyer is not null)>0 " +
            "order by u.lastName, u.firstName")
    List<User> findAllBySoldProductsIsNotNullOrderByLastNameAscFirstName();

}
