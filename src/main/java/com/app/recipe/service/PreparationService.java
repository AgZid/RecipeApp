package com.app.recipe.service;

import com.app.recipe.modal.Ingredient;
import com.app.recipe.modal.Preparation;
import com.app.recipe.modal.Recipe;
import com.app.recipe.repository.PreparationRepository;
import com.app.recipe.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PreparationService {
    private final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    PreparationRepository preparationRepository;
    RecipeRepository recipeRepository;

    public PreparationService(PreparationRepository preparationRepository, RecipeRepository recipeRepository) {
        this.preparationRepository = preparationRepository;
        this.recipeRepository = recipeRepository;
    }

    public ResponseEntity<Ingredient> update(Integer id, Preparation preparation) {
        LOGGER.info("Update ingredient id {}", id);
        Optional<Preparation> foundPreparation = preparationRepository.findById(id);
        if (foundPreparation.isPresent() && preparation != null) {
            foundPreparation.map(
                    preparation1 -> {
                        preparation1.setStepNo(preparation.getStepNo());
                        preparation1.setStepAction(preparation.getStepAction());
                        return preparationRepository.save(preparation1);
                    }
            );
            LOGGER.info("Preparation part updated");
            return new ResponseEntity(preparationRepository.findById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> removeById(Integer id) {
        try {
            preparationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Integer> create(Preparation preparation) {
        if (isPreparationValid(preparation)) {
            LOGGER.info("Create new preparation {}.", preparation.getStepAction());
            Preparation savedPreparation = preparationRepository.saveAndFlush(preparation);
            return new ResponseEntity(savedPreparation.getId(), HttpStatus.CREATED);
        } else {
            LOGGER.info("Preparation () was not saved", preparation.getStepNo());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Recipe> assignPreparationToRecipe(Integer preparationId, Integer recipeId) {
        LOGGER.info("Assign preparation {} to recipe {}.", preparationId, recipeId);
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        Optional<Preparation> preparation = preparationRepository.findById(preparationId);
        if (recipe.isPresent() && preparation.isPresent()) {
            preparation.get().setRecipe(recipe.get());
            preparationRepository.save(preparation.get());
            LOGGER.info("Preparation assigned to recipe.");
            return new ResponseEntity(recipeRepository.findById(recipeId), HttpStatus.OK);
        } else {
            LOGGER.info("Preparation could not be assigned to recipe.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isPreparationValid(Preparation preparation) {
        return (preparation.getStepNo() > 0 && preparation.getStepAction() != null);
    }

}
