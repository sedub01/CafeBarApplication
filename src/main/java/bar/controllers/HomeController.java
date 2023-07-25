package bar.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import bar.security.User;

@Controller //main annotation's purpose - identify class as a Component for scan Components mechanism.
public class HomeController {
    
    @GetMapping("/") //GET-request
    public String home(){
        return "home"; // for home.html view
    }

    @ModelAttribute
    public void insertFullname(Model model,
    @AuthenticationPrincipal User user){
        if (user != null)
            model.addAttribute("fullname", user.getFullname());
    }
}
