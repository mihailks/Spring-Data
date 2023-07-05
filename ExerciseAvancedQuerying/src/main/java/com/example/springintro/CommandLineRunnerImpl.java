package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        System.out.println("Select exercise number:");
        int number = Integer.parseInt(bufferedReader.readLine());

        switch (number) {
            case 1 -> printAllBooksByAgeRestriction();
            case 2 -> printAllGoldenBooks();
            case 3 -> printAllBooksByPricesInRange();
            case 4 -> printAllNotReleasedBooks();
            case 5 -> printAllBooksReleasedBeforeDate();
            case 6 -> printAllAuthorsSearch();
            case 7 -> printAllBooksSearch();
            case 8 -> printAllBookTitlesSearchByAuthorNameContains();
            case 9 -> printCountBooks();
            case 10 -> printTotalBookCopiesByAuthor();
            case 11 -> ReducedBookByTitle();
            case 12 -> IncreaseBookCopies();
            case 13 -> RemoveBooks();
            case 14 -> SPCountBooksByAuthor();
            case 99 -> testQ();
        }
    }

    private void SPCountBooksByAuthor() throws IOException {
        System.out.println("Enter name:");
        String[] input = bufferedReader.readLine().split(" ");
        String first_name = input[0];
        String last_name = input[1];
        int countOfBooks = authorService.findBooksByAuthor(first_name, last_name);
        System.out.printf("%s %s has written %d book", first_name, last_name, countOfBooks);
    }

    private void RemoveBooks() throws IOException {
        System.out.println("Enter copies");
        int copies = Integer.parseInt(bufferedReader.readLine());
        int affectedRows = bookService.RemoveBooksWithFewerCopiesThen(copies);
        System.out.println(affectedRows);
    }

    private void ReducedBookByTitle() throws IOException {
        System.out.println("Enter title:");
        String title = bufferedReader.readLine();
        bookService.findAndReduceInfoForBook(title)
                .forEach(System.out::println);
    }


    private void IncreaseBookCopies() throws IOException {
        System.out.println("enter date");
        LocalDate localDate = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd MMM yyyy"));
        System.out.println("Enter copies");
        int copies = Integer.parseInt(bufferedReader.readLine());
        System.out.println(bookService.increaseCopiesByDate(localDate, copies));
    }

    private void testQ() {
        bookService.changeBookPrice(1L);
    }

    private void printTotalBookCopiesByAuthor() {
        authorService.findTotalBookCopiesByAuthor()
                .forEach(System.out::println);
    }

    private void printCountBooks() throws IOException {
        int minLength = Integer.parseInt(bufferedReader.readLine());
        int countOfBooks = bookService.printAllBooksTitleLongerThenNumber(minLength);
        System.out.println(countOfBooks);
    }

    private void printAllBookTitlesSearchByAuthorNameContains() throws IOException {
        System.out.println("Enter str");
        String input = bufferedReader.readLine();
        bookService.findAllBookTitlesSearchByAuthorNameContains(input)
                .forEach(System.out::println);
    }

    private void printAllBooksSearch() throws IOException {
        System.out.println("Enter str");
        String input = bufferedReader.readLine();
        bookService.findAllBooksTitleContains(input)
                .forEach(System.out::println);
    }

    private void printAllAuthorsSearch() throws IOException {
        System.out.println("Enter input");
        String endStr = bufferedReader.readLine();
        authorService.findAuthorFirstAndLastNameByEndStr(endStr)
                .forEach(System.out::println);
    }

    private void printAllBooksReleasedBeforeDate() throws IOException {
        System.out.println("Enter date");
//        String[] input = bufferedReader.readLine().split("-");
//        int day = Integer.parseInt(input[0]);
//        int month = Integer.parseInt(input[1]);
//        int year = Integer.parseInt(input[2]);

        LocalDate localDate = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyy"));

        bookService.findAllBooksBeforeDate(localDate)
                .forEach(System.out::println);
    }

    private void printAllNotReleasedBooks() throws IOException {
        System.out.println("Enter year");
        int year = Integer.parseInt(bufferedReader.readLine());
        bookService.findAllNotReleasedBooks(year)
                .forEach(System.out::println);
    }

    private void printAllBooksByPricesInRange() {
        bookService.findAllBooksInPriceRange()
                .forEach(System.out::println);
    }

    private void printAllGoldenBooks() {
        bookService.findAllGoldenBooks()
                .forEach(System.out::println);
    }

    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }

    private void printAllBooksByAgeRestriction() throws IOException {
        String input = bufferedReader.readLine();
        AgeRestriction ageRestriction = AgeRestriction.valueOf(input.toUpperCase());
        bookService.printAllBooksByAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

}
