package bg.softuni.exercisespringdataintro.service;

import bg.softuni.exercisespringdataintro.model.entity.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategory();
}
