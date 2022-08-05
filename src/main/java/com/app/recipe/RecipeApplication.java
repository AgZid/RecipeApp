package com.app.recipe;

import com.app.recipe.Enumeration.MeasureUnit;
import com.app.recipe.modal.Ingredient;
import com.app.recipe.modal.Preparation;
import com.app.recipe.modal.Recipe;
import com.app.recipe.repository.IngredientRepository;
import com.app.recipe.repository.PreparationRepository;
import com.app.recipe.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class RecipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeApplication.class, args);
	}

	@Bean //aplikacijos paleidimo metu graznamas instansas bus idedamas i Spring konteineri
	public CommandLineRunner constructCommandLineRunnerBean(final RecipeRepository recipeRepository,
															final IngredientRepository ingredientRepository,
															final PreparationRepository preparationRepository)
	{

		return args -> {

			Recipe recipe = Recipe.builder()
					.title("Omletas")
					.rating(4.1)
					.totalTime("15")
					.numberOfServings(2)
					.build();
			recipeRepository.save(recipe);

			Ingredient egg = Ingredient.builder()
					.product("kiausinis")
					.measurementUnit(MeasureUnit.VNT)
					.quantity(3.0)
					.recipe(recipe)
					.build();
			ingredientRepository.save(egg);

			Ingredient milk = Ingredient.builder()
					.product("pienas")
					.measurementUnit(MeasureUnit.ML)
					.quantity(100.0)
					.recipe(recipe)
					.build();
			ingredientRepository.save(milk);

			Ingredient salt = Ingredient.builder()
					.product("druska")
					.measurementUnit(MeasureUnit.G)
					.quantity(3.0)
					.recipe(recipe)
					.build();
			ingredientRepository.save(salt);

			Preparation preparation1 = Preparation.builder()
					.recipe(recipe)
					.stepNo(1)
					.stepAction("Sumaišyti kiausinius, pieną ir druską")
					.build();
			preparationRepository.save(preparation1);

			Preparation preparation2 = Preparation.builder()
					.recipe(recipe)
					.stepNo(2)
					.stepAction("Kepti 5 min. nuolat pamaišant")
					.build();
			preparationRepository.save(preparation2);

		};
	}

}
