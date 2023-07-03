package bg.softuni.exercisespringdataintro.service.impl;

import bg.softuni.exercisespringdataintro.model.entity.Author;
import bg.softuni.exercisespringdataintro.repository.AuthorRepository;
import bg.softuni.exercisespringdataintro.service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final String AUTHORS_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\ExerciseSpringDataIntro\\src\\main\\resources\\files\\authors.txt";

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {

        if (authorRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(AUTHORS_FILE_PATH))
                .forEach(authorNames -> {
                    String first_name = authorNames.split("\\s+")[0];
                    String last_name = authorNames.split("\\s+")[1];
                    Author author = new Author(first_name, last_name);
                    authorRepository.save(author);
                });
    }

    @Override
    public Author getRandomAuthor() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, authorRepository.count() + 1);
        return authorRepository.findById(randomId)
                .orElse(null);
    }

    @Override
    public List<String> getAllAuthorsOrderByCountOfTheirBooks() {
        return authorRepository
                .findAllByBooksSizeDESC()
                .stream()
                .map(author -> String.format("%s %s %d",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size()))
                .collect(Collectors.toList());
    }
}
