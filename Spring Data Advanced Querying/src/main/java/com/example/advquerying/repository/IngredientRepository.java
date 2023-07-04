package com.example.advquerying.repository;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {


//    @Query("UPDATE Ingredient as i set i.price = i.price*1.1")
    @Modifying
    @Transactional
    @Query("UPDATE Ingredient as i set i.name = concat(i.name, 'Updated') ")
    void increasePriceBy10Percent();

@Transactional
    void deleteIngredientByName(String name);

}
