package bg.softuni.exercisejsonprocessing.servise;

import bg.softuni.exercisejsonprocessing.model.entity.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;
    Set<Category> findRandomCategories();
}
