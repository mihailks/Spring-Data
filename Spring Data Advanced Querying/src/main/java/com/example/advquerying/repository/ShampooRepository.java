package com.example.advquerying.repository;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findBySizeOrderById(Size size);

    List<Shampoo> findBySizeOrLabel_IdOrderByPrice(Size size, Long label_id);

    List<Shampoo> findByPriceIsGreaterThanOrderByPriceDesc(BigDecimal price);


    @Query(value = "SELECT s from Shampoo as s Join s.ingredients as i where i.name IN :ingredientNames")
    List<Shampoo> findByIngredientsNameIn(List<String> ingredientNames);




}

