package bar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauth2/authorization")
public class OAuth2Controller {

    @GetMapping("/*")
    public String getLoginPage() {
        return "redirect:/register";
    }
}
