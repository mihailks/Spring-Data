package bg.softuni.exercisespringdataintro.service.impl;

import bg.softuni.exercisespringdataintro.model.entity.*;
import bg.softuni.exercisespringdataintro.repository.BookRepository;
import bg.softuni.exercisespringdataintro.service.AuthorService;
import bg.softuni.exercisespringdataintro.service.BookService;
import bg.softuni.exercisespringdataintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final String BOOK_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\ExerciseSpringDataIntro\\src\\main\\resources\\files\\books.txt";


    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }


    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }


        Files.readAllLines(Path.of(BOOK_FILE_PATH))
                .forEach(row -> {
                    String[] bookData = row.split("\\s+");
                    Book book = createBookFromData(bookData);
                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository.findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {

        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAutorFirstLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());

    }


    private Book createBookFromData(String[] bookData) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookData[0])];
        LocalDate releaseDate = LocalDate.parse(bookData[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookData[2]);
        BigDecimal price = new BigDecimal(bookData[3]);
        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookData[4])];

        String title = Arrays.stream(bookData)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();

        Set<Category> categories = categoryService.getRandomCategory();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);
    }


}
