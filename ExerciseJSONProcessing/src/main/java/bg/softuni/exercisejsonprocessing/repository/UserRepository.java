package bg.softuni.exercisejsonprocessing.repository;

import bg.softuni.exercisejsonprocessing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

        @Query("select u from User as u " +
            "where(select count(p) from Product as p " +
            "where p.seller.id = u.id) > 0 " +
            "order by u.lastName, u.firstName")

//@Query("SELECT u FROM User u " +
//        "JOIN u.soldProducts p "+
//        "WHERE p.buyer IS NOT NULL" +
//        " order by u.lastName,u.firstName")
    List<User> findAllUsersWithMoreThenProductsSoldOrderByLastNameFirstName();


        @Query("select u from User u " +
                "join u.soldProducts p " +
                "where p.buyer is not null " +
                "order by size(u.soldProducts) desc, u.lastName ")
        List<User> findUserBySoldProducts();




}
