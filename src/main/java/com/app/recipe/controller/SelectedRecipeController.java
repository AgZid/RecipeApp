package com.app.recipe.controller;

import com.app.recipe.exception.InvalidArgumentException;
import com.app.recipe.modal.SelectedRecipe;
import com.app.recipe.service.SelectedRecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/recipes/selected")
@CrossOrigin(origins = "http://localhost:4200")
public class SelectedRecipeController {

    private SelectedRecipeService selectedRecipeService;

    public SelectedRecipeController(SelectedRecipeService selectedRecipeService) {
        this.selectedRecipeService = selectedRecipeService;
    }

    @GetMapping("/ingredients")
    public Map<String, Double> findAllIngredients() throws InvalidArgumentException {
        return selectedRecipeService.findAllSelectedIngredients();
    }

    @GetMapping
    public ResponseEntity<List<SelectedRecipe>> findAll() {
        return selectedRecipeService.findAll();
    }

    @PostMapping
    public ResponseEntity<List<SelectedRecipe>> create(@RequestBody SelectedRecipe selectedRecipe) {
        return selectedRecipeService.create(selectedRecipe);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return selectedRecipeService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SelectedRecipe> update(
            @PathVariable("id") Integer id,
            @RequestBody SelectedRecipe selectedRecipe) {
        return selectedRecipeService.update(id, selectedRecipe);
    }

}
