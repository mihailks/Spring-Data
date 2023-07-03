package bg.softuni.exercisespringdataintro;

import bg.softuni.exercisespringdataintro.model.entity.Book;
import bg.softuni.exercisespringdataintro.service.AuthorService;
import bg.softuni.exercisespringdataintro.service.BookService;
import bg.softuni.exercisespringdataintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfBooks();
        printAllBooksByAuthorNameOrderByReleaseDate("George", "Powell");
    }

    private void printAllBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAutorFirstLastNameOrderByReleaseDate(firstName,lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfBooks() {
        authorService.
                getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService.findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }


    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
