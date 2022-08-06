package com.app.recipe.controller;

import com.app.recipe.exception.NoResultFound;
import com.app.recipe.modal.Ingredient;
import com.app.recipe.modal.Recipe;
import com.app.recipe.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/recipes")
@CrossOrigin(origins = "http://localhost:4200")
public class IngredientController {

    IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}/ingredients")
    public ResponseEntity<List<Ingredient>> findByRecipeId(@PathVariable("recipeId") Integer recipeId) throws NoResultFound {
        return ingredientService.findByRecipeId(recipeId);
    }

    @DeleteMapping("/ingredient/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        return ingredientService.removeById(id);
    }

    @PutMapping("/ingredient/{id}")
    public ResponseEntity<Ingredient> update(
            @PathVariable("id") Integer id,
            @RequestBody Ingredient ingredient) throws NoResultFound {
        return ingredientService.update(id, ingredient);
    }

    @PostMapping("/ingredient")
    public ResponseEntity<Integer> create(@RequestBody Ingredient ingredient) {
        return ingredientService.create(ingredient);
    }

    @PutMapping("/ingredient/{ingredientId}/recipe/{recipeId}")
    public ResponseEntity<Recipe> addIngredientToRecipe(@PathVariable("ingredientId") int ingredientId,
                                                          @PathVariable("recipeId") int recipeId) {
        return ingredientService.assignIngredientToRecipe(ingredientId, recipeId);
    }

}
