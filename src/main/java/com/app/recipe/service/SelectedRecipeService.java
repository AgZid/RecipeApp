package com.app.recipe.service;

import com.app.recipe.exception.InvalidArgumentException;
import com.app.recipe.modal.Ingredient;
import com.app.recipe.modal.Recipe;
import com.app.recipe.modal.SelectedRecipe;
import com.app.recipe.repository.RecipeRepository;
import com.app.recipe.repository.SelectedRecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SelectedRecipeService {

    private final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    private final SelectedRecipeRepository selectedRecipeRepository;
    private final RecipeRepository recipeRepository;

    public SelectedRecipeService(SelectedRecipeRepository selectedRecipeRepository,
                                 RecipeRepository recipeRepository) {
        this.selectedRecipeRepository = selectedRecipeRepository;
        this.recipeRepository = recipeRepository;
    }

    public ResponseEntity<List<SelectedRecipe>> create(SelectedRecipe newSelectedRecipe) {
        if (isValidSelectedRecipe(newSelectedRecipe)) {
            if (isRecipeInSelectedRecipesList(newSelectedRecipe.getRecipeId())) {
                LOGGER.info("Updating selectedRecipe entity {}.", newSelectedRecipe.getNumberOfServings());
                SelectedRecipe selectedRecipe = selectedRecipeRepository.findSelectedRecipeByRecipeId(newSelectedRecipe.getRecipeId());
                selectedRecipe.setNumberOfServings(selectedRecipe.getNumberOfServings() + newSelectedRecipe.getNumberOfServings());
                selectedRecipeRepository.save(selectedRecipe);
                return new ResponseEntity(selectedRecipeRepository.findAll(), HttpStatus.OK);
            } else {
                LOGGER.info("Create new selectedRecipe entity {}.", newSelectedRecipe.getNumberOfServings());
                selectedRecipeRepository.save(newSelectedRecipe);
                return new ResponseEntity(selectedRecipeRepository.findAll(), HttpStatus.CREATED);
            }
        } else {
            LOGGER.info("Incorrect selectedRecipe entity {}.", newSelectedRecipe.getNumberOfServings());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<SelectedRecipe>> findAll() {
        LOGGER.info("Finding all selected recipes.");
        return new ResponseEntity(selectedRecipeRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<SelectedRecipe> update(Integer id, SelectedRecipe updatedSelectedRecipe) {
        Optional<SelectedRecipe> selectedRecipe = selectedRecipeRepository.findById(id);
        if (selectedRecipe.isPresent() && isValidSelectedRecipe(updatedSelectedRecipe)) {
            LOGGER.info("Update selected recipe");
            selectedRecipe.get().setNumberOfServings(updatedSelectedRecipe.getNumberOfServings());
            selectedRecipeRepository.save(selectedRecipe.get());
            return new ResponseEntity(selectedRecipeRepository.findById(id), HttpStatus.OK);
        } else {
            LOGGER.info("Incorrect data, selected recipe was not updated");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> delete(Integer id) {
        try {
            selectedRecipeRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    public Map<String, Double> findAllSelectedIngredients() throws InvalidArgumentException {
        List<SelectedRecipe> selectedRecipes = selectedRecipeRepository.findAll();
        int numberOfServings = 1;
        List<Ingredient> listOfIngredientsList = new ArrayList<>();

        for (SelectedRecipe selectedRecipe : selectedRecipes) {
            numberOfServings = selectedRecipe.getNumberOfServings();
            Recipe recipe = recipeRepository.findById(selectedRecipe.getRecipeId()).get();

            List<Ingredient> multipliedIngredients =
                    multiplyIngredients(numberOfServings, recipe.getNumberOfServings(), recipe.getIngredients());
            multipliedIngredients.forEach(System.out::println);
            listOfIngredientsList.addAll(multipliedIngredients);

        }

        return listOfIngredientsList.stream()
                .collect(Collectors.groupingBy(ingredient -> ingredient.getProduct() + " (" + ingredient.getMeasurementUnit() + ")",
                        Collectors.summingDouble(Ingredient::getQuantity)
                ));

    }

    protected List<Ingredient> multiplyIngredients(int selectedNumberOfServings,
                                                   int numberOfServings,
                                                   List<Ingredient> ingredients) throws InvalidArgumentException {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getQuantity() <= 0 || numberOfServings <= 0 || selectedNumberOfServings <= 0) {
                throw new InvalidArgumentException("Some values are negative or equal to 0");
            }
            ingredient.setQuantity(ingredient.getQuantity() / numberOfServings * selectedNumberOfServings);
        }
        return ingredients;
    }

    private boolean isRecipeInSelectedRecipesList(Integer recipeId) {
        System.out.println("Recipe id: " + recipeId);
        Integer selectedRecipeIdByRecipeId = selectedRecipeRepository.findSelectedRecipeIdByRecipeId(recipeId);
        return selectedRecipeIdByRecipeId != null;
    }

    private boolean isValidSelectedRecipe(SelectedRecipe selectedRecipe) {
        return (selectedRecipe.getRecipeId() != null && selectedRecipe.getNumberOfServings() > 0);
    }
}


