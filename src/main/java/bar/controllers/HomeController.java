package bar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //main annotation's purpose - identify class as a Component for scan Components mechanism.
public class HomeController {
    
    @GetMapping("/") //GET-request
    public String home(){
        return "home"; // for home.html view
    }
}
