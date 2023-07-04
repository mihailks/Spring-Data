package com.example.advquerying;

import com.example.advquerying.servise.IngredientService;
import com.example.advquerying.servise.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private ShampooService shampooService;
    private IngredientService ingredientService;

    public CommandLineRunnerImpl(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }


    @Override
    public void run(String... args) throws Exception {

//        1. Select Shampoos by Size
//        shampooService.findBySize(Size.MEDIUM)
//                .forEach(System.out::println);

//        2. Select Shampoos by Size or Label
//        shampooService.findBySizeOrLabelId(Size.MEDIUM, 10L)
//                .forEach(System.out::println);

//        3. Select Shampoos by Price

//        shampooService.selectByPrice(BigDecimal.valueOf(5))
//                .forEach(System.out::println);

//        7. Select Shampoos by Ingredients
//        List<Shampoo> result = shampooService.findAllWithIngredients(List.of("Berry","Mineral-Collagen"));
//        result.forEach(System.out::println);

//        9. Delete Ingredients by Name
            ingredientService.deleteByName("Apple");
//        10. Update Ingredients by Price
//            ingredientService.increasePrice();


    }
}
