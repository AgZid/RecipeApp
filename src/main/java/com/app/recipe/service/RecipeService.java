package com.app.recipe.service;

import com.app.recipe.exception.NoResultFound;
import com.app.recipe.modal.Recipe;
import com.app.recipe.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;
    private  final SelectedRecipeService selectedRecipeService;

    public RecipeService(RecipeRepository recipeRepository, SelectedRecipeService selectedRecipeService) {
        this.recipeRepository = recipeRepository;
        this.selectedRecipeService = selectedRecipeService;
    }

    public ResponseEntity<List<Recipe>> findAll() {
        LOGGER.info("Finding all recipes.");
        return new ResponseEntity(recipeRepository.findAll(), HttpStatus.OK);
    }

    public List<Recipe> findByTitle(String title) {
        LOGGER.info("Finding recipe by title {}", title);
        return recipeRepository.findByTitle(title);
    }

    public Recipe findById(Integer id) {
        LOGGER.info("Finding recipe by id {}", id);
        return recipeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found with id : " + id));
    }

    public ResponseEntity<Recipe> create(Recipe recipe) {
        if (recipe != null) {
            LOGGER.info("Create new recipe {}.", recipe.getTitle());
            return new ResponseEntity(recipeRepository.saveAndFlush(recipe), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public void removeById(Integer id) {
        LOGGER.info("Checking if recipe id {} is in selected recipe list.", id);
        if (findById(id) != null && !selectedRecipeService.isRecipeInSelectedRecipesList(id)) {
            LOGGER.info("Remove recipe id {}.", id);
            recipeRepository.deleteById(id);
        } else {
            LOGGER.info("Recipe id {} is in selected recipe list and cannot be removed.", id);
        }
    }

    public ResponseEntity<Recipe> update(Integer id, Recipe recipe) throws NoResultFound {
        LOGGER.info("Updating recipe  id {} to {} ", id, recipe.getTitle());
        Optional<Recipe> foundRecipe = recipeRepository.findById(id);

        if (foundRecipe.isPresent() && isValidRecipeArguments(recipe)) {
            LOGGER.info("Updating recipe {} to {} ", foundRecipe.get().getTitle(), recipe.getTitle());
            foundRecipe.map(
                    recipe1 -> {
                        recipe1.setTitle(recipe.getTitle());
                        recipe1.setNumberOfServings(recipe.getNumberOfServings());
                        recipe1.setRating(recipe.getRating());
                        recipe1.setTotalTime(recipe.getTotalTime());
                        return recipeRepository.save(recipe1);
                    }
            );
            LOGGER.info("Recipe updated");
            return new ResponseEntity(recipeRepository.findById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public boolean isValidRecipeId(Integer id) throws NoResultFound {
        LOGGER.info("Finding recipe by id {}", id);
        {
            Optional<Recipe> foundRecipe = recipeRepository.findById(id);
            if (foundRecipe.isEmpty()) {
                throw new NoResultFound("Recipe not found with id " + id);
            } else return true;
        }
    }

    private boolean isValidRecipeArguments(Recipe recipe) {
        return recipe.getTitle() != null && recipe.getNumberOfServings() > 0;
    }
}
