package bg.softuni.exercisespringdataintro.service;

import bg.softuni.exercisespringdataintro.model.entity.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAutorFirstLastNameOrderByReleaseDate(String firstName, String lastName);

}
