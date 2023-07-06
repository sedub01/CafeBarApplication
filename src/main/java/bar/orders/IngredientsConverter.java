package bar.orders;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import bar.coffee.CoffeeIngredient;
import bar.coffee.CoffeeIngredient.Type;

//Converting Strings to CoffeeIngredient objects
@Component
public class IngredientsConverter implements Converter<String, CoffeeIngredient>{

    private Map<String, CoffeeIngredient> ingredientsMap = new HashMap<>();

    public IngredientsConverter(){
        ingredientsMap.put("ESP", new CoffeeIngredient("ESP", "Espresso", Type.ESPRESSO, 0));
        ingredientsMap.put("UMI", new CoffeeIngredient("UMI", "Cow Milk", Type.MILK, 0));
        ingredientsMap.put("AMI", new CoffeeIngredient("AMI", "Almond Milk", Type.MILK, 0));
        ingredientsMap.put("SUG", new CoffeeIngredient("SUG", "Sugar", Type.SUGAR, 0));
        ingredientsMap.put("WAT", new CoffeeIngredient("WAT", "Water", Type.WATER, 0));
        ingredientsMap.put("CRE", new CoffeeIngredient("CRE", "Cream", Type.CREAM, 0));
        ingredientsMap.put("MCH", new CoffeeIngredient("MCH", "Milk Chocolate", Type.CHOCOLATE, 0));
        ingredientsMap.put("BCH", new CoffeeIngredient("BCH", "Bitter Chocolate", Type.CHOCOLATE, 0));
        ingredientsMap.put("CTO", new CoffeeIngredient("CTO", "Chocolate", Type.TOPPING, 0));
        ingredientsMap.put("MTO", new CoffeeIngredient("MTO", "Marshmallow", Type.TOPPING, 0));
    }

    @Override
    public CoffeeIngredient convert(String id) {
        return ingredientsMap.get(id);
    }
    
}
