package bg.softuni.exercisespringdataintro.service.impl;

import bg.softuni.exercisespringdataintro.model.entity.Category;
import bg.softuni.exercisespringdataintro.repository.CategoryRepository;
import bg.softuni.exercisespringdataintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private static final String CATEGORIES_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\ExerciseSpringDataIntro\\src\\main\\resources\\files\\categories.txt";

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }
        Files.readAllLines(Path.of(CATEGORIES_FILE_PATH))
                .stream()
                .filter(r -> !r.isEmpty())
                .forEach(r -> {
                    Category category = new Category(r);
                    categoryRepository.save(category);
                });
    }

    @Override
    public Set<Category> getRandomCategory() {
        Set<Category> categories = new HashSet<>();

        int randInt = ThreadLocalRandom.current().nextInt(1, 3);
        long numberOfCategoriesInRepo = categoryRepository.count() + 1;
        for (int i = 0; i < randInt; i++) {
            long randId = ThreadLocalRandom.current().nextLong(1, numberOfCategoriesInRepo);

            Category category = categoryRepository.findById(randId)
                    .orElse(null);

            categories.add(category);

        }
        return categories;
    }

}
