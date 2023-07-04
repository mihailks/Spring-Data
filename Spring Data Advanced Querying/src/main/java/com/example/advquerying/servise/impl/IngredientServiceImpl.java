package com.example.advquerying.servise.impl;

import com.example.advquerying.repository.IngredientRepository;
import com.example.advquerying.servise.IngredientService;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void increasePrice() {
        ingredientRepository.increasePriceBy10Percent();
    }

    @Override
    public void deleteByName(String name) {
        ingredientRepository.deleteIngredientByName(name);
    }
}
