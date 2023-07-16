package bg.softuni.exercisejsonprocessing.servise.impl;

import bg.softuni.exercisejsonprocessing.model.DTO.CategoryProductCountDTO;
import bg.softuni.exercisejsonprocessing.model.DTO.CategorySeedDTO;
import bg.softuni.exercisejsonprocessing.model.entity.Category;
import bg.softuni.exercisejsonprocessing.repository.CategoryRepository;
import bg.softuni.exercisejsonprocessing.servise.CategoryService;
import bg.softuni.exercisejsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static bg.softuni.exercisejsonprocessing.constanst.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public static final String CATEGORIES_FILE_NAME = "categories.json";

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }
        String fileContent = Files
                .readString(Path.of(RESOURCES_FILE_PATH + CATEGORIES_FILE_NAME));

        CategorySeedDTO[] categorySeedDTOs = gson.fromJson(fileContent, CategorySeedDTO[].class);

        Arrays.stream(categorySeedDTOs)
                .filter(validationUtil::isValid)
                .map(categorySeedDTO -> modelMapper.map(categorySeedDTO, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> findRandomCategories() {
        Set<Category> categorySet = new HashSet<>();

        int catCount = ThreadLocalRandom
                .current().nextInt(1, 4);

        long categoryRepositorySize = categoryRepository.count();
        for (int i = 0; i < catCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, categoryRepositorySize + 1);

            categorySet.add(categoryRepository.findById(randomId).orElse(null));
        }
        return categorySet;
    }

    @Override
    public List<CategoryProductCountDTO> countCategories() {
        return categoryRepository.findAllByNumberOfProducts();
    }


}
