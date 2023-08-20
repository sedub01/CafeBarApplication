package bar;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import bar.coffee.Coffee;
import bar.coffee.Ingredient;
import bar.coffee.Ingredient.Type;
import bar.controllers.DesignCoffeeController;
import bar.data.GenderRepository;
import bar.data.IngredientRepository;
import bar.data.OrderRepository;
import bar.data.UserRepository;
import bar.orders.CoffeeOrder;
import bar.security.Gender;
import bar.security.User;
import bar.utils.Role;

@WebMvcTest(DesignCoffeeController.class)
public class DesignCoffeeControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    private List<Ingredient> ingredients;
    private Coffee coffeeDesign;

    @MockBean
    private IngredientRepository ingredientRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private GenderRepository genderRepository;
    @MockBean
    private PasswordEncoder encoder;

    @BeforeEach
    public void setup(){
        ingredients = Arrays.asList(
            new Ingredient("UMI", "Cow Milk", Type.MILK, 0),
			new Ingredient("AMI", "Almond Milk", Type.MILK, 0),
			new Ingredient("SUG", "Sugar", Type.SUGAR, 0),
			new Ingredient("WAT", "Water", Type.WATER, 0),
			new Ingredient("CRE", "Cream", Type.CREAM, 0),
			new Ingredient("MCH", "Milk Chocolate", Type.CHOCOLATE, 0),
			new Ingredient("BCH", "Bitter Chocolate", Type.CHOCOLATE, 0),
			new Ingredient("CTO", "Chocolate", Type.TOPPING, 0),
			new Ingredient("MTO", "Marshmallow", Type.TOPPING, 0),
			new Ingredient("ESP", "Espresso", Type.ESPRESSO, 0)
        );

        when(ingredientRepository.findAll()).thenReturn(ingredients);

        when(ingredientRepository.findById("CTO")).thenReturn(
            Optional.of(new Ingredient("CTO", "Chocolate", Type.TOPPING, 0))
            );
        when(ingredientRepository.findById("AMI")).thenReturn(
            Optional.of(new Ingredient("AMI", "Almond Milk", Type.MILK, 0))
            );

        coffeeDesign = new Coffee();
        coffeeDesign.setName("Test Coffee");
        coffeeDesign.setIngredients(Arrays.asList(
            new Ingredient("UMI", "Cow Milk", Type.MILK, 0),
            new Ingredient("SUG", "Sugar", Type.SUGAR, 0),
            new Ingredient("WAT", "Water", Type.WATER, 0),
            new Ingredient("ESP", "Espresso", Type.ESPRESSO, 0)
        ));
        //No need to use encoder
        when(userRepository.findByUsername("testuser"))
    		.thenReturn(new User("testuser", "testpass", 
            "Test User", "4809",
            "test.user@gmail.com", Role.USER,
            new Gender()));
    }

    @Test
    @WithMockUser(username="testuser", password="testpass")
    public void testShowDesignForm() throws Exception {
        mockMvc.perform(get("/design"))
            .andExpect(status().isOk())
            .andExpect(view().name("design"))
            .andExpect(model().attribute("milk", ingredients.subList(0, 2)))
            .andExpect(model().attribute("sugar", ingredients.subList(2, 3)))
            .andExpect(model().attribute("water", ingredients.subList(3, 4)))
            .andExpect(model().attribute("cream", ingredients.subList(4, 5)))
            .andExpect(model().attribute("chocolate", ingredients.subList(5, 7)))
            .andExpect(model().attribute("topping", ingredients.subList(7, 9)))
            .andExpect(model().attribute("espresso", ingredients.subList(9, 10)));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", authorities = "ROLE_USER")
    public void processCoffee() throws Exception {
        CoffeeOrder order = new CoffeeOrder();
        order.addCoffee(coffeeDesign);
        when(orderRepository.save(order)).thenReturn(order);

        mockMvc.perform(post("/design").with(csrf())
                .content("name=Test+Coffee&ingredients=UMI,SUG,WAT,ESP")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/orders/current"));
    }
}
