package bar.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import bar.coffee.Ingredient;
import bar.coffee.Ingredient.Type;
import bar.data.IngredientRepository;
import bar.data.UserRepository;
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
	public CommandLineRunner usersDataLoader(UserRepository repo, PasswordEncoder encoder){
		return args -> {
			if (!repo.existsByUsername("admin")) //User can't contain String as ID, like in prev. method
				repo.save(new User("admin",
					encoder.encode("password"),
					"Admin Adminoff",
					"09900",
					"admin@gmail.com",
					Role.ADMIN));
		};
	}
}
