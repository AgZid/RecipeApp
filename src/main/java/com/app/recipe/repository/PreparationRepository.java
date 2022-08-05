package com.app.recipe.repository;

import com.app.recipe.modal.Preparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreparationRepository extends JpaRepository<Preparation, Integer> {
    @Query("SELECT pr FROM Preparation pr where recipe_id = :recipeId")
    List<String> findByRecipeId(@Param("recipeId") Integer recipeId);
}
