package com.example.advquerying.servise.impl;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repository.ShampooRepository;
import com.example.advquerying.servise.ShampooService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<String> findBySize(Size size) {
        return shampooRepository.findBySizeOrderById(size)
                .stream()
                .map(shampoo -> String.format("%s %s %.2f",
                        shampoo.getBrand(),
                        shampoo.getSize(),
                        shampoo.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findBySizeOrLabelId(Size size, Long id) {
        return shampooRepository.findBySizeOrLabel_IdOrderByPrice(size, id)
                .stream()
                .map(shampoo -> String.format("%s %s %.2f",
                        shampoo.getBrand(),
                        shampoo.getSize(),
                        shampoo.getPrice()))
                .collect(Collectors.toList());
    }

    public List<String> selectByPrice(BigDecimal price) {
        return shampooRepository.findByPriceIsGreaterThanOrderByPriceDesc(price)
                .stream()
                .map(shampoo -> String.format("%s %s %.2f",
                        shampoo.getBrand(),
                        shampoo.getSize(),
                        shampoo.getPrice()))
                .collect(Collectors.toList());

    }

    @Override
    public List<Shampoo> findAllWithIngredients(List<String> ingredientNames) {
        return shampooRepository.findByIngredientsNameIn(ingredientNames);
    }
}
