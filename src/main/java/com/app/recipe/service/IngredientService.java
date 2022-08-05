package com.app.recipe.service;

import com.app.recipe.modal.Ingredient;
import com.app.recipe.modal.Recipe;
import com.app.recipe.repository.IngredientRepository;
import com.app.recipe.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class IngredientService {

    private final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    IngredientRepository ingredientRepository;
    RecipeRepository recipeRepository;

    public IngredientService(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    public ResponseEntity<List<Ingredient>> findByRecipeId(Integer recipeId) {
        LOGGER.info("Finding all ingredients for specific recipe {}", recipeId);
        return new ResponseEntity(ingredientRepository.findByRecipeId(recipeId), HttpStatus.OK);
    }

    public ResponseEntity<Ingredient> update(Integer id, Ingredient ingredient) {
        LOGGER.info("Update ingredient id {}", id);
        Ingredient updatedIngredient = ingredientRepository.findById(id).get();
        updatedIngredient.setProduct(ingredient.getProduct());
        updatedIngredient.setQuantity(ingredient.getQuantity());
        updatedIngredient.setMeasurementUnit(ingredient.getMeasurementUnit());
        ingredientRepository.save(updatedIngredient);
        return new ResponseEntity(ingredientRepository.findById(id), HttpStatus.OK);
    }

    public ResponseEntity<String> removeById(Integer id) {
        try {
            ingredientRepository.deleteById(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (RuntimeException ex) {
            // log the error message
            System.out.println(ex.getMessage());
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Integer> create(Ingredient ingredient) {
        LOGGER.info("Create new ingredient {}.", ingredient.getProduct());
        Ingredient savedIngredient = ingredientRepository.saveAndFlush(ingredient);
        return new ResponseEntity(savedIngredient.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity<Recipe> assignIngredientToRecipe(Integer ingredientId, Integer recipeId) {
        LOGGER.info("Assign ingredient {} to recipe {}.", ingredientId, recipeId);
        Recipe recipe = recipeRepository.findById(recipeId).get();
        Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
        ingredient.setRecipe(recipe);
        ingredientRepository.save(ingredient);
        LOGGER.info("Ingredient assigned to recipe.");
        return new ResponseEntity(recipeRepository.findById(recipeId), HttpStatus.OK);
    }

}
