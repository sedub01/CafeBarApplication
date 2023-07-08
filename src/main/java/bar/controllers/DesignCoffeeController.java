package bar.controllers;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import bar.coffee.Coffee;
import bar.coffee.Ingredient;
import bar.coffee.Ingredient.Type;
import bar.data.IngredientRepository;
import bar.orders.CoffeeOrder;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("coffeeOrder")
public class DesignCoffeeController {
    
    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignCoffeeController(
        IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type: types){
            model.addAttribute(type.toString().toLowerCase(), 
                filterByType(ingredients, type));
        }
    }

    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
            .filter(x -> x.getType().equals(type))
            .collect(Collectors.toList());
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
