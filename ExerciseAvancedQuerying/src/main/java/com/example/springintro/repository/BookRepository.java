package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findBookByAgeRestriction(AgeRestriction input);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal price, BigDecimal price2);

    List<Book> findByReleaseDateLessThanOrReleaseDateGreaterThan(LocalDate releaseDate, LocalDate releaseDate2);

    List<Book> findByTitleIsContainingIgnoreCase(String title);

    List<Book> findBookByAuthor_LastNameStartsWith(String inputStr);

    @Query("select count(b) from Book b where length(b.title)>:minLength")
    int findAllBooksTitleLongerThenInput(int minLength);


    //PROCEDURE
    @Procedure("change_book_price_by_id")
    void ChangeBookPriceById(Long book_id);

    @Modifying
    @Query("update Book b set b.copies = b.copies+ :copies where b.releaseDate> :date")
    int updateCopiesByReleaseDate(@Param(value = "copies") int copies,
                                  @Param(value = "date") LocalDate localDate);

    List<Book> findBookByTitle(String title);

    @Modifying
    int deleteBookByCopiesLessThan(int copies);

}
