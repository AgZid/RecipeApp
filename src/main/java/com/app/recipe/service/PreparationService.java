package com.app.recipe.service;

import com.app.recipe.modal.Ingredient;
import com.app.recipe.modal.Preparation;
import com.app.recipe.modal.Recipe;
import com.app.recipe.repository.IngredientRepository;
import com.app.recipe.repository.PreparationRepository;
import com.app.recipe.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreparationService {
    private final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    PreparationRepository preparationRepository;
    RecipeRepository recipeRepository;

    public PreparationService(PreparationRepository preparationRepository, RecipeRepository recipeRepository) {
        this.preparationRepository = preparationRepository;
        this.recipeRepository = recipeRepository;
    }

    public ResponseEntity<List<Ingredient>> findByRecipeId(Integer recipeId) {
        LOGGER.info("Finding all ingredients for specific recipe {}", recipeId);
        return new ResponseEntity(preparationRepository.findByRecipeId(recipeId), HttpStatus.OK);
    }

    public ResponseEntity<Ingredient> update(Integer id, Preparation preparation) {
        LOGGER.info("Update ingredient id {}", id);
        Preparation updatedPreparation = preparationRepository.findById(id).get();
        updatedPreparation.setStepNo(preparation.getStepNo());
        updatedPreparation.setStepAction(preparation.getStepAction());
        preparationRepository.save(updatedPreparation);
        return new ResponseEntity(preparationRepository.findById(id), HttpStatus.OK);
    }

    public ResponseEntity<String> removeById(Integer id) {
        try {
            preparationRepository.deleteById(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (RuntimeException ex) {
            // log the error message
            System.out.println(ex.getMessage());
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Integer> create(Preparation preparation) {
        LOGGER.info("Create new ingredient {}.", preparation.getStepAction());
        Preparation savedPreparation = preparationRepository.saveAndFlush(preparation);
        return new ResponseEntity(savedPreparation.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity<Recipe> assignPreparationToRecipe(Integer preparationId, Integer recipeId) {
        LOGGER.info("Assign preparation {} to recipe {}.", preparationId, recipeId);
        Recipe recipe = recipeRepository.findById(recipeId).get();
        Preparation preparation = preparationRepository.findById(preparationId).get();
        preparation.setRecipe(recipe);
        preparationRepository.save(preparation);
        LOGGER.info("Preparation assigned to recipe.");
        return new ResponseEntity(recipeRepository.findById(recipeId), HttpStatus.OK);
    }

}
