package bar.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import bar.coffee.Ingredient;
import bar.data.IngredientRepository;

//Converting Strings to CoffeeIngredient objects
@Component
public class IngredientsConverter implements Converter<String, Ingredient>{

    private IngredientRepository ingredientRepo;

    @Autowired
    public IngredientsConverter(IngredientRepository ingredientRepo){
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(id).orElse(null);
    }
    
}
