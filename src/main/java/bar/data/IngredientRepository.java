package bar.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bar.coffee.Ingredient;

//Repository for storing ingredients
@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String>{
    
}
