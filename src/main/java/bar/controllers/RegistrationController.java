package bar.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bar.data.GenderRepository;
import bar.data.UserRepository;
import bar.security.RegistrationForm;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private GenderRepository genderRepo;

    // @Autowired //not necessary
    public RegistrationController(
        UserRepository userRepo,
        GenderRepository genderRepo,
        PasswordEncoder passwordEncoder
    ){
        this.userRepo = userRepo;
        this.genderRepo = genderRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registrationForm(Model model){
        model.addAttribute("genders", genderRepo.findAll());
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form){
        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
