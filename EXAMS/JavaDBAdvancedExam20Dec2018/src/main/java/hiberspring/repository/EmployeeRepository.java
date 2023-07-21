package hiberspring.repository;

import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByCard_Number(String number);

    @Query("select e from Employee e " +
            "where e.branch.products.size>0" +
            " order by concat(e.firstName, e.lastName), length(e.position) desc")
    List<Employee> findAllByBranchWithMoreThenOneProduct();

}
