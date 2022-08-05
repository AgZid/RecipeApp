package com.app.recipe.repository;


import com.app.recipe.modal.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    @Query("SELECT ing FROM Ingredient ing WHERE recipe_id = :recipeId ORDER BY step_no")
    List<String> findByRecipeId(@Param("recipeId") Integer recipeId);


}

