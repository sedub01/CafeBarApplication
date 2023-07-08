package bar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import bar.coffee.Ingredient;
import bar.coffee.Ingredient.Type;
import bar.data.IngredientRepository;

@SpringBootApplication
public class CafeBarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeBarApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo) {
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
		};
	}
}
