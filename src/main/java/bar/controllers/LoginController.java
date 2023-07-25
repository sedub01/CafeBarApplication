package bar.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    
    private static String authorizationRequestBaseUri
      = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls
      = new HashMap<>();
      
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping
    public String getLoginPage(Model model) {
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
            .as(Iterable.class);
        if (type != ResolvableType.NONE && 
            ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            @SuppressWarnings("unchecked")
            var clientRegistrations = 
                (Iterable<ClientRegistration>) clientRegistrationRepository;
            clientRegistrations.forEach(registration -> 
            oauth2AuthenticationUrls.put(registration.getClientName(), 
            authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        }
        model.addAttribute("oauth2_urls", oauth2AuthenticationUrls);

        return "login";
    }
}
