package bg.softuni.exercisexmlprocessing.servise;

import bg.softuni.exercisexmlprocessing.model.DTO.CategorySeedDTO;
import bg.softuni.exercisexmlprocessing.model.entity.Category;


import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(List<CategorySeedDTO> categories);
    long getEntityCount();

    Set<Category> getRandomCategories();

}
