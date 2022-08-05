package com.app.recipe.controller;


import com.app.recipe.modal.Ingredient;
import com.app.recipe.modal.Preparation;
import com.app.recipe.modal.Recipe;
import com.app.recipe.service.PreparationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/recipes")
@CrossOrigin(origins = "http://localhost:4200")
public class PreparationController {

    PreparationService preparationService;

    public PreparationController(PreparationService preparationService) {
        this.preparationService = preparationService;
    }

    @DeleteMapping("/preparation/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        return preparationService.removeById(id);
    }

    @PutMapping("/preparation/{id}")
    public ResponseEntity<Ingredient> update(
            @PathVariable("id") Integer id,
            @RequestBody Preparation preparation) {
        return preparationService.update(id, preparation);
    }

    @PostMapping("/preparation")
    public ResponseEntity<Integer> create(@RequestBody Preparation preparation) {
        return preparationService.create(preparation);
    }

    @PutMapping("/preparation/{preparationId}/recipe/{recipeId}")
    public ResponseEntity<Recipe> addPreparationToRecipe(@PathVariable("preparationId") int preparationId,
                                                        @PathVariable("recipeId") int recipeId) {
        return preparationService.assignPreparationToRecipe(preparationId, recipeId);
    }

}
