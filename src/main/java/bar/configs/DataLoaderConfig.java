package bar.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import bar.coffee.Ingredient;
import bar.coffee.Ingredient.Type;
import bar.data.GenderRepository;
import bar.data.IngredientRepository;
import bar.data.UserRepository;
import bar.security.Gender;
import bar.security.User;
import bar.utils.Role;

@Profile("!prod")
@Configuration
public class DataLoaderConfig {
    
    @Bean
	public CommandLineRunner ingredientsDataLoader(IngredientRepository repo) {
		return args -> {
			repo.save(new Ingredient("UMI", "Cow Milk", Type.MILK, 0));
			repo.save(new Ingredient("AMI", "Almond Milk", Type.MILK, 0));
			repo.save(new Ingredient("SUG", "Sugar", Type.SUGAR, 0));
			repo.save(new Ingredient("WAT", "Water", Type.WATER, 0));
			repo.save(new Ingredient("CRE", "Cream", Type.CREAM, 0));
			repo.save(new Ingredient("MCH", "Milk Chocolate", Type.CHOCOLATE, 0));
			repo.save(new Ingredient("BCH", "Bitter Chocolate", Type.CHOCOLATE, 0));
			repo.save(new Ingredient("CTO", "Chocolate", Type.TOPPING, 0));
			repo.save(new Ingredient("MTO", "Marshmallow", Type.TOPPING, 0));
			repo.save(new Ingredient("ESP", "Espresso", Type.ESPRESSO, 0));
		};
	}

	@Bean
	public CommandLineRunner genderDataLoader(GenderRepository genderRepo){
		return args -> {
			genderRepo.save(new Gender("Traditional Male"));
			genderRepo.save(new Gender("Traditional Female"));
			genderRepo.save(new Gender("Metrosexual Male"));
			genderRepo.save(new Gender("Metrosexual Female"));
			genderRepo.save(new Gender("Male, But Curious What Being a Female in Like"));
			genderRepo.save(new Gender("Female, But Curious What Being a Male in Like"));
			genderRepo.save(new Gender("Male, But Overweight So Has Boobs"));
			genderRepo.save(new Gender("Female, But Have an Adam's apple"));
			genderRepo.save(new Gender("Hermaphrodite with Predominant Male Leanings"));
			genderRepo.save(new Gender("Hermaphrodite with Predominant Female Leanings"));
			genderRepo.save(new Gender("Hermaphrodite with No Strong Gender Leanings"));
			genderRepo.save(new Gender("Conjoined Twin-Male"));
			genderRepo.save(new Gender("Conjoined Twin-Female"));
			genderRepo.save(new Gender("Born Without Genitals - Identify an в Main"));
			genderRepo.save(new Gender("Born Without Genitals - Identify as a Female"));
			genderRepo.save(new Gender("Born Without Genitals - Proud of it"));
			genderRepo.save(new Gender("Born a Male, Bad Circumcision, Raised Female"));
			genderRepo.save(new Gender("Sentient Artificial Intelligence With No Gender"));
			genderRepo.save(new Gender("Sentient Artificial Intelligence - Identifies as Male"));
			genderRepo.save(new Gender("Sentient Artificial Intelligence - Identifies as Female"));
			genderRepo.save(new Gender("Household Pet That Walked Across the Keyboard - Male"));
			genderRepo.save(new Gender("Household Pet That Walked Across the Keyboard - Female"));
			genderRepo.save(new Gender("Household Pet That Walked Across the Keyboard - Other"));
			genderRepo.save(new Gender("Genderfluid Helicopter"));
			genderRepo.save(new Gender("Other"));
			genderRepo.save(new Gender("None"));
			genderRepo.save(new Gender("Prefer not to say"));
		};
	}

	@Bean
	public CommandLineRunner usersDataLoader(UserRepository repo,
	GenderRepository genderRepository,
	PasswordEncoder encoder){
		return args -> {
			if (!repo.existsByUsername("admin")) //User can't contain String as ID, like in prev. method
				repo.save(new User("admin",
					encoder.encode("password"),
					"Admin Adminoff",
					"09900",
					"admin@gmail.com",
					Role.ADMIN,
					genderRepository.findById(2)));
		};
	}
}
