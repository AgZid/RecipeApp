package com.app.recipe.controller;

import com.app.recipe.exception.NoResultFound;
import com.app.recipe.modal.Recipe;
import com.app.recipe.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/app/recipes")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> findAll() {
        return recipeService.findAll();
    }

    @GetMapping("/title")
    public List<Recipe> findByName(@RequestParam String title) {
        return recipeService.findByTitle(title);
    }

    @PostMapping
    public ResponseEntity<Recipe> create(@Valid @RequestBody Recipe recipe) {
        return recipeService.create(recipe);
    }

    @GetMapping("/{id}")
    public Recipe findById(@PathVariable("id") Integer id) {
        return recipeService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable("id") Integer id,@Valid @RequestBody Recipe recipe) throws NoResultFound {
        return recipeService.update(id, recipe);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable("id") Integer id) {
        recipeService.removeById(id);
    }


}
