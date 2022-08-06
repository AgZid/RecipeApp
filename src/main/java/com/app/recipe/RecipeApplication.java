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

			Recipe sokoladainis = Recipe.builder()
					.title("Šokoladainis")
					.rating(4.5)
					.totalTime("60 min")
					.numberOfServings(8)
					.build();
			recipeRepository.save(sokoladainis);
			Ingredient sokoladas = Ingredient.builder().product("Juodas šokoladas").measurementUnit(MeasureUnit.G)
					.quantity(300.0).recipe(sokoladainis).build();
			ingredientRepository.save(sokoladas);

			Ingredient sviestas1 = Ingredient.builder().product("Sviestas").measurementUnit(MeasureUnit.G)
					.quantity(250.0).recipe(sokoladainis).build();
			ingredientRepository.save(sviestas1);

			Ingredient cukrus1 = Ingredient.builder().product("Cukrus").measurementUnit(MeasureUnit.G)
					.quantity(250.0).recipe(sokoladainis).build();
			ingredientRepository.save(cukrus1);

			Ingredient kiausiniai1 = Ingredient.builder().product("Kiaušiniai").measurementUnit(MeasureUnit.VNT)
					.quantity(4.0).recipe(sokoladainis).build();
			ingredientRepository.save(kiausiniai1);

			Ingredient kakava = Ingredient.builder().product("Kakava").measurementUnit(MeasureUnit.G)
					.quantity(40.0).recipe(sokoladainis).build();
			ingredientRepository.save(kakava);

			Ingredient miltai1 = Ingredient.builder().product("Miltai").measurementUnit(MeasureUnit.G)
					.quantity(180.0).recipe(sokoladainis).build();
			ingredientRepository.save(miltai1);

			Ingredient druska = Ingredient.builder().product("Druska").measurementUnit(MeasureUnit.G)
					.quantity(2.0).recipe(sokoladainis).build();
			ingredientRepository.save(druska);

			Ingredient kepimoMilteliai = Ingredient.builder().product("Kepimo milteliai").measurementUnit(MeasureUnit.G)
					.quantity(2.0).recipe(sokoladainis).build();
			ingredientRepository.save(kepimoMilteliai);

			Ingredient rududu = Ingredient.builder().product("Rududu indelis").measurementUnit(MeasureUnit.VNT)
					.quantity(0.3).recipe(sokoladainis).build();
			ingredientRepository.save(rududu);

			Ingredient riesutai = Ingredient.builder().product("Lazdyno riešutai").measurementUnit(MeasureUnit.G)
					.quantity(50.0).recipe(sokoladainis).build();
			ingredientRepository.save(riesutai);

			Ingredient nutela = Ingredient.builder().product("Nutela riešutų svieto mažas indelis").measurementUnit(MeasureUnit.VNT)
					.quantity(0.2).recipe(sokoladainis).build();
			ingredientRepository.save(nutela);

			Preparation step1 = Preparation.builder().recipe(sokoladainis).stepNo(1)
					.stepAction("Sviestą su šokoladu ištirpinti").build();
			preparationRepository.save(step1);

			Preparation step2= Preparation.builder().recipe(sokoladainis).stepNo(2)
					.stepAction("Išplakti kiaušinius su cukrumi").build();
			preparationRepository.save(step2);

			Preparation step3= Preparation.builder().recipe(sokoladainis).stepNo(3)
					.stepAction("Kakavą sumaišyti su miltais ir kepimo milteliais").build();
			preparationRepository.save(step3);

			Preparation step4= Preparation.builder().recipe(sokoladainis).stepNo(4)
					.stepAction("Šokolado ir sviesto masę supilti į kiaušinių ir cukraus masę ir išmaišyti su miltais").build();
			preparationRepository.save(step4);

			Preparation step5= Preparation.builder().recipe(sokoladainis).stepNo(5)
					.stepAction("Masę supilti į kepimo indą").build();
			preparationRepository.save(step5);

			Preparation step6= Preparation.builder().recipe(sokoladainis).stepNo(6)
					.stepAction("Kas keli cm įspausti po 0,3 a. šaukstelio rududu, nutelos ir 1 riešutą").build();
			preparationRepository.save(step6);

			Preparation step7= Preparation.builder().recipe(sokoladainis).stepNo(7)
					.stepAction("Kepame 170˚C orkaitėje 25 min. ").build();
			preparationRepository.save(step7);

			Recipe pyragas = Recipe.builder()
					.title("Greitas pyragas su kefyru")
					.totalTime("40 min")
					.numberOfServings(4)
					.build();
			recipeRepository.save(pyragas);

			Ingredient kiausiniai2 = Ingredient.builder().product("Kiaušiniai").measurementUnit(MeasureUnit.VNT)
					.quantity(1.0).recipe(pyragas).build();
			ingredientRepository.save(kiausiniai2);

			Ingredient kefyras = Ingredient.builder().product("Kefyras").measurementUnit(MeasureUnit.ML)
					.quantity(200.0).recipe(pyragas).build();
			ingredientRepository.save(kefyras);

			Ingredient cukrus2 = Ingredient.builder().product("Cukrus").measurementUnit(MeasureUnit.G)
					.quantity(200.0).recipe(pyragas).build();
			ingredientRepository.save(cukrus2);

			Ingredient sviestas2 = Ingredient.builder().product("Sviestas").measurementUnit(MeasureUnit.G)
					.quantity(10.0).recipe(pyragas).build();
			ingredientRepository.save(cukrus2);

			Ingredient miltai2 = Ingredient.builder().product("Miltai").measurementUnit(MeasureUnit.G)
					.quantity(200.0).recipe(pyragas).build();
			ingredientRepository.save(miltai2);

			Ingredient soda = Ingredient.builder().product("Soda").measurementUnit(MeasureUnit.G)
					.quantity(5.0).recipe(pyragas).build();
			ingredientRepository.save(soda);

			Preparation step21= Preparation.builder().recipe(pyragas).stepNo(1)
					.stepAction("Išplakite kiaušinį su cukrumi, įpilkite kefyro, lydyto sviesto, sodos ir miltų. Viską gerai išmaišykite").build();
			preparationRepository.save(step21);

			Preparation step22= Preparation.builder().recipe(pyragas).stepNo(2)
					.stepAction("supilti į kepimo formą ir kepti 180 laipsnių tempreratūroje apie 20 min").build();
			preparationRepository.save(step22);

			Recipe bulviuLaiveliai = Recipe.builder()
					.title("Bulvių laiveliai")
					.totalTime("1 val 40 min")
					.numberOfServings(2)
					.build();
			recipeRepository.save(bulviuLaiveliai);

			Ingredient bulves = Ingredient.builder().product("Bulvės").measurementUnit(MeasureUnit.VNT)
					.quantity(4.0).recipe(bulviuLaiveliai).build();
			ingredientRepository.save(bulves);

			Ingredient sonine = Ingredient.builder().product("Kiaulienos šoninė").measurementUnit(MeasureUnit.G)
					.quantity(250.0).recipe(bulviuLaiveliai).build();
			ingredientRepository.save(sonine);

			Ingredient suris = Ingredient.builder().product("Grietinėlės sūris").measurementUnit(MeasureUnit.G)
					.quantity(250.0).recipe(bulviuLaiveliai).build();
			ingredientRepository.save(suris);


			Preparation step31= Preparation.builder().recipe(pyragas).stepNo(1)
					.stepAction("Įkaitinti orkaitę iki 200 laipsnių, 4 bulves su lupenomis subadyti šakute ir dėkite į skardą. Kepti apie 1 val.").build();
			preparationRepository.save(step31);

			Preparation step32= Preparation.builder().recipe(pyragas).stepNo(2)
					.stepAction("Smulkiai supjaustykite 250 g šoninės ir gerai pakepkite. Kai bulvės iškeps, leiskite joms atvėsti, vėliau supjaustykite per pusę ir išskobkite dalį minkštimo, kad išeitų laiveliai").build();
			preparationRepository.save(step32);

			Preparation step33= Preparation.builder().recipe(pyragas).stepNo(3)
					.stepAction("Išskobtą bulvių masę sutrinkite, sumaišykite su kepta šonine bei grietinėlės sūriu. Gautą masę dėkite į „laivelius“ ir kepkite orkaitėje dar 15 min.").build();
			preparationRepository.save(step33);

		};
	}

}
