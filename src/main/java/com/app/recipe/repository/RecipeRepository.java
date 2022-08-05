package com.app.recipe.repository;

import com.app.recipe.modal.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("SELECT re FROM Recipe re WHERE re.title LIKE %:title%")
    List<Recipe> findByTitle(@Param("title") String title);
//    List<Recipe> findByTitle(String title);

}
