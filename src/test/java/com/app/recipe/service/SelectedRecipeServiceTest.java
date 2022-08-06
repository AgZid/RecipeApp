package com.app.recipe.service;

import com.app.recipe.Enumeration.MeasureUnit;
import com.app.recipe.exception.InvalidArgumentException;
import com.app.recipe.modal.Ingredient;
import com.app.recipe.modal.Recipe;
import com.app.recipe.modal.SelectedRecipe;
import com.app.recipe.repository.RecipeRepository;
import com.app.recipe.repository.SelectedRecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SelectedRecipeServiceTest {

    public SelectedRecipeService testService;
    private Recipe recipe;
    private SelectedRecipe selectedRecipe;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    SelectedRecipeRepository selectedRecipeRepository;

    @BeforeEach
    public void init() {
        testService = new SelectedRecipeService(selectedRecipeRepository, recipeRepository);

        Ingredient pienas1 = Ingredient.builder()
                .product("Pienas")
                .measurementUnit(MeasureUnit.ML)
                .quantity(100.0)
                .build();
        Ingredient pienas2 = Ingredient.builder()
                .product("Pienas")
                .measurementUnit(MeasureUnit.ML)
                .quantity(20.0)
                .build();
        Ingredient kiausiniai = Ingredient.builder()
                .product("Kiausiniai")
                .measurementUnit(MeasureUnit.VNT)
                .quantity(2.0)
                .build();
        List<Ingredient> ingredients = List.of(pienas1, pienas2, kiausiniai);

        recipe = Recipe.builder()
                .id(1)
                .title("Test")
                .ingredients(ingredients)
                .numberOfServings(2)
                .build();

        selectedRecipe = SelectedRecipe.builder()
                .recipeId(1)
                .numberOfServings(4)
                .build();
    }

    @Test
    void testMultiplyIngredients() throws InvalidArgumentException {
        int selectedNumberOfServings = 2;
        int numberOfServings = 4;

        Ingredient pienasML = Ingredient.builder()
                .product("Pienas")
                .measurementUnit(MeasureUnit.ML)
                .quantity(100.0)
                .build();

        List<Ingredient> ingredients = List.of(pienasML);

        List<Ingredient> result = testService
                .multiplyIngredients(selectedNumberOfServings, numberOfServings, ingredients);

        assertEquals(50.0, Objects.requireNonNull(result.stream().filter(ingredient -> ingredient.getProduct().equals("Pienas"))
                .findFirst().orElse(null)).getQuantity());
    }

    @Test
    void testMultiplyIngredients_ThrowsException() {
        Ingredient pienasML = Ingredient.builder()
                .product("Pienas")
                .measurementUnit(MeasureUnit.ML)
                .quantity(-50.0)
                .build();

        List<Ingredient> ingredients = List.of(pienasML);
        assertThrows(InvalidArgumentException.class, () -> testService
                .multiplyIngredients(0, 1, ingredients));
    }

    @Test
    void testFindAllSelectedIngredients() throws InvalidArgumentException {

        Map<String, Double> expectedList = new HashMap<>();
        expectedList.put("Pienas (ML)", 240.0);
        expectedList.put("Kiausiniai (VNT)", 4.0);

        when(recipeRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(recipe));
        when(selectedRecipeRepository.findAll()).thenReturn((List.of(selectedRecipe)));

        final Map<String, Double> allSelectedIngredients = testService.findAllSelectedIngredients();
        assertEquals(allSelectedIngredients, expectedList);

    }

    @Test
    void testUpdate_selectedRecipeIsEmpty() {
        when(selectedRecipeRepository.findById(1)).thenReturn(java.util.Optional.empty());
        assertEquals(HttpStatus.BAD_REQUEST, testService.update(1, selectedRecipe).getStatusCode());
    }
}