package bg.softuni.exercisexmlprocessing.servise.impl;

import bg.softuni.exercisexmlprocessing.model.DTO.CategorySeedDTO;
import bg.softuni.exercisexmlprocessing.model.entity.Category;
import bg.softuni.exercisexmlprocessing.repository.CategoryRepository;
import bg.softuni.exercisexmlprocessing.servise.CategoryService;
import bg.softuni.exercisexmlprocessing.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories(List<CategorySeedDTO> categories) {
        categories
                .stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDTO -> modelMapper.map(categorySeedDTO, Category.class))
                .forEach(categoryRepository::save);

    }

    @Override
    public long getEntityCount() {
        return categoryRepository.count();
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        long categoriesCount = categoryRepository.count();
        for (int i = 0; i < 2; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, categoriesCount + 1);
            categories.add(categoryRepository.findById(randomId).orElse(null));
        }

        return categories;
    }
}
