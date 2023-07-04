package com.example.advquerying.servise;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<String> findBySize(Size size);

    List<String> findBySizeOrLabelId(Size size, Long id);

    List<String> selectByPrice(BigDecimal price);

    List<Shampoo> findAllWithIngredients(List<String> ingredientNames);
}