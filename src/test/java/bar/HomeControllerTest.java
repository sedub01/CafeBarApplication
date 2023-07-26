package bar;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import bar.data.IngredientRepository;
import bar.data.OrderRepository;
import bar.data.UserRepository;
import bar.service.OrderAdminService;

@SpringBootTest
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // Note: Most of these mocks are here to avoid autowiring issues. They aren't
    // actually used in the course of the home page test, so their behavior
    // isn't important. They just need to exist so autowiring can take place.

    @MockBean
    private OrderAdminService adminService;
    @MockBean
    private IngredientRepository ingredientRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/")) //execute GET-request
                .andExpect(status().isOk())   //expecting responce code 200
                .andExpect(view().name("home")) //expecting home.html view
                .andExpect(content().string( //expecting containing "Welcome to..." string
                        containsString("Welcome")));
    }
}
