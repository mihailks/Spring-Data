package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> printAllBooksByAgeRestriction(AgeRestriction input);

    List<String> findAllGoldenBooks();

    List<String> findAllBooksInPriceRange();

    List<String> findAllNotReleasedBooks(int year);

    List<String> findAllBooksBeforeDate(LocalDate localDate);

    List<String> findAllBooksTitleContains(String input);

    List<String> findAllBookTitlesSearchByAuthorNameContains(String input);

    int printAllBooksTitleLongerThenNumber(int minLength);

    void changeBookPrice(long book_id);

    int increaseCopiesByDate(LocalDate localDate, int copies);

    List<String> findAndReduceInfoForBook(String title);

    int RemoveBooksWithFewerCopiesThen(int copies);
}
