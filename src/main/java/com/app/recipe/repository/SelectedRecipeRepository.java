package com.app.recipe.repository;

import com.app.recipe.modal.Recipe;
import com.app.recipe.modal.SelectedRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectedRecipeRepository extends JpaRepository<SelectedRecipe, Integer> {

    @Query("SELECT re.title FROM Recipe re, SelectedRecipe se where re.id = se.recipeId")
    List<String> findTitlesOfSelectedRecipes();

    @Query("SELECT re FROM Recipe re where re.id in " +
            "   (SELECT se.recipeId FROM SelectedRecipe se)")
    List<Recipe> findAllSelectedRecipes();

    @Query("SELECT se.id FROM SelectedRecipe se where recipeId =:recipeId")
    Integer findSelectedRecipeIdByRecipeId(@Param("recipeId") Integer recipeId);

    @Query("SELECT se FROM SelectedRecipe se where recipeId =:recipeId")
    SelectedRecipe findSelectedRecipeByRecipeId(@Param("recipeId") Integer recipeId);

//    @Query("SELECT re FROM Recipe re where re.id in " +
//            "   (SELECT se.recipeId FROM SelectedRecipe se)")
//    List<Recipe> findSelectedRecipesByRecipeId();

//    @Query("SELECT se.numberOfServings FROM SelectedRecipe se where se.recipeId =:id")
//    Integer findNumberOfServings(@Param("id") Integer id);
}
