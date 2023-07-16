package com.example.springintro.repository;

import com.example.springintro.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findAuthorByFirstNameEndingWith(String endStr);

    @Procedure("select_books_written_by_the_author")
            //FIXME: PROBLEM WITH THE PARAMETERS
int countBooksByTheSameAuthor(@Param(value = "first_name") String firstName, @Param(value = "last_name") String lastName);
//    int countBooksByTheSameAuthor(String firstName, String lastName);


}
