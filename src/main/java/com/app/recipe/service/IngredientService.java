package com.app.recipe.service;

import com.app.recipe.exception.NoResultFound;
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
import java.util.Optional;

@Service
public class IngredientService {

    private final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    IngredientRepository ingredientRepository;
    RecipeRepository recipeRepository;
    RecipeService recipeService;

    public IngredientService(IngredientRepository ingredientRepository,
                             RecipeRepository recipeRepository,
                             RecipeService recipeService) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
    }

    public ResponseEntity<List<Ingredient>> findByRecipeId(Integer recipeId) {
        LOGGER.info("Finding all ingredients for specific recipe {}", recipeId);
        List<String> ingredientsByRecipeId = ingredientRepository.findByRecipeId(recipeId);
        if (ingredientsByRecipeId.isEmpty()) {
            LOGGER.info("No ingredients for specific recipe id {}", recipeId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(ingredientsByRecipeId, HttpStatus.OK);
    }

    public ResponseEntity<Ingredient> update(Integer id, Ingredient ingredient) throws NoResultFound {
        LOGGER.info("Updating ingredient id {}", id);
        Optional<Ingredient> foundIngredient = ingredientRepository.findById(id);
        if (foundIngredient.isPresent() && ingredient != null) {
            foundIngredient.map(
                    ingredient1 -> {
                        ingredient1.setProduct(ingredient.getProduct());
                        ingredient1.setQuantity(ingredient.getQuantity());
                        ingredient1.setMeasurementUnit(ingredient.getMeasurementUnit());
                        return ingredientRepository.save(ingredient1);
                    }
            );
            LOGGER.info("Ingredient updated");
            return new ResponseEntity(ingredientRepository.findById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> removeById(Integer id) {
        try {
            ingredientRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Integer> create(Ingredient ingredient) {
        if (isIngredientValid(ingredient)) {
            LOGGER.info("Create new ingredient {}.", ingredient.getProduct());
            Ingredient savedIngredient = ingredientRepository.saveAndFlush(ingredient);
            return new ResponseEntity(savedIngredient.getId(), HttpStatus.CREATED);
        } else {
            LOGGER.info("Ingredient () was not saved", ingredient.getProduct());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Recipe> assignIngredientToRecipe(Integer ingredientId, Integer recipeId) {
        LOGGER.info("Assign ingredient {} to recipe {}.", ingredientId, recipeId);
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
        if (recipe.isPresent() && ingredient.isPresent()) {
            ingredient.get().setRecipe(recipe.get());
            ingredientRepository.save(ingredient.get());
            LOGGER.info("Ingredient assigned to recipe.");
            return new ResponseEntity(recipeRepository.findById(recipeId), HttpStatus.OK);
        } else {
            LOGGER.info("Ingredient could not be assigned to recipe.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isIngredientValid(Ingredient ingredient) {
        return (ingredient.getProduct() != null &&
                ingredient.getMeasurementUnit() != null &&
                ingredient.getQuantity() > 0);
    }

}
