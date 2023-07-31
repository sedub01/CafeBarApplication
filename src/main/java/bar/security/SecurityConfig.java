package bar.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;

import bar.data.UserRepository;
import bar.utils.Role;

@Configuration
@EnableMethodSecurity //for global security use (and @PreAuthorize too)
public class SecurityConfig {
    
    //annotation means that this method creates and configures bean
    //Method returns object, which will be registered in Spring context as Bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> { //functional interface
            User user = userRepo.findByUsername(username);
            if (user != null) {
                return user;
            }
            throw new UsernameNotFoundException(
                    "User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/design", "/orders").hasAuthority(Role.USER)
                    .requestMatchers("/admin/**").hasAuthority(Role.ADMIN)
                    //resources below are available without registration
                    .requestMatchers("/", "/**").permitAll())
            .formLogin(login -> login //login conf. stage
                    .loginPage("/login")
                    //redirected path after login (if second param is true, redirection will be forced)
                    .defaultSuccessUrl("/design", true))
            .oauth2Login(oauth2Login -> oauth2Login
                    .loginPage("/login")
                    .failureUrl("/loginFailure")
                    .authorizationEndpoint(endpoint -> endpoint
                        .baseUri("/oauth2/authorize-client")
                        .authorizationRequestRepository(authorizationRequestRepository())
                        )
                    )
            .logout(logout -> logout
                    .logoutSuccessUrl("/"));
        return http.build();
        //Used https://www.baeldung.com/spring-security-5-oauth2-login
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest>
    authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }
}
