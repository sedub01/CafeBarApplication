package bar.coffee;

import lombok.Data;

@Data
public class CoffeeIngredient {
    
    private final String id;
    private final String name;
    private final Type type;
    private final double dose; //in fractions of a unit

    public enum Type {
        ESPRESSO, MILK, SUGAR, WATER, CREAM, CHOCOLATE, TOPPING
    }
}
