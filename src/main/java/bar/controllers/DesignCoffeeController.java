package bar.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import bar.coffee.Coffee;
import bar.coffee.CoffeeIngredient;
import bar.coffee.CoffeeIngredient.Type;
import bar.orders.CoffeeOrder;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("coffeeOrder")
public class DesignCoffeeController {
    
    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<CoffeeIngredient> ingredients = Arrays.asList( //like a database
            new CoffeeIngredient("ESP", "Espresso", Type.ESPRESSO, 0),
            new CoffeeIngredient("UMI", "Cow Milk", Type.MILK, 0),
            new CoffeeIngredient("AMI", "Almond Milk", Type.MILK, 0),
            new CoffeeIngredient("SUG", "Sugar", Type.SUGAR, 0),
            new CoffeeIngredient("WAT", "Water", Type.WATER, 0),
            new CoffeeIngredient("CRE", "Cream", Type.CREAM, 0),
            new CoffeeIngredient("MCH", "Milk Chocolate", Type.CHOCOLATE, 0),
            new CoffeeIngredient("BCH", "Bitter Chocolate", Type.CHOCOLATE, 0),
            new CoffeeIngredient("CTO", "Chocolate", Type.TOPPING, 0),
            new CoffeeIngredient("MTO", "Marshmallow", Type.TOPPING, 0)
        );
        Type[] types = CoffeeIngredient.Type.values();
        for (Type type: types){
            model.addAttribute(type.toString().toLowerCase(), 
                filterByType(ingredients, type));
        }
    }

    private Iterable<CoffeeIngredient> filterByType(List<CoffeeIngredient> ingredients, Type type) {
        return ingredients.stream().filter(x->x.getType().equals(type)).collect(Collectors.toList());
    }

    @ModelAttribute(name = "coffeeOrder")
    public CoffeeOrder order(){
        //contains state of wanted order, while client chooses ingredients by several requests
        return new CoffeeOrder();
    }

    @ModelAttribute(name = "coffee")
    public Coffee coffee(){
        return new Coffee();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    @PostMapping
    public String processCoffee(@Valid Coffee coffee,
        Errors errors, 
        @ModelAttribute CoffeeOrder coffeeOrder){
        if (errors.hasErrors()){
            return "design";
        }
        coffeeOrder.addCoffee(coffee);
        log.info("Processing coffee: {}", coffee);
//redirect means that after processCoffee completed browser has to open another page, 
//sending GET-request with "/orders/current" path
        return "redirect:/orders/current";
    }
}
