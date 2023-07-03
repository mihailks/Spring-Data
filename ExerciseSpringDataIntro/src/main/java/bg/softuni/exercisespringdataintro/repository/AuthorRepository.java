package bg.softuni.exercisespringdataintro.repository;

import bg.softuni.exercisespringdataintro.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select a from Author a order by size(a.books) desc")
    List<Author> findAllByBooksSizeDESC();
}
