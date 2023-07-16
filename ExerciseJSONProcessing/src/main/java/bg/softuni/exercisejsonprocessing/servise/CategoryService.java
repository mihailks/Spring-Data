package bg.softuni.exercisejsonprocessing.servise;

import bg.softuni.exercisejsonprocessing.model.DTO.CategoryProductCountDTO;
import bg.softuni.exercisejsonprocessing.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;
    Set<Category> findRandomCategories();

    List<CategoryProductCountDTO> countCategories();
}
