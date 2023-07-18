package bar.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import bar.data.UserRepository;

@Configuration
@EnableMethodSecurity //for global security use (and @PreAuthorize too)
public class SecurityConfig {
    
    //annotation means that this method creates and configures bean
    //Method returns object, which will be registrated in Spring context as Bean 
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
                    .requestMatchers("/design", "/orders").hasRole("USER")
                    .requestMatchers("/admin/**").hasRole("USER")
                    //resources below are available without registration
                    .requestMatchers("/", "/**").permitAll())
            .formLogin(login -> login //login conf. stage
                    .loginPage("/login")
                    //redirected path after login (if second param is true, redirection will be forced)
                    .defaultSuccessUrl("/design", true))
            .oauth2Login(oauth2Login -> oauth2Login
                    .loginPage("/login")
                    .failureUrl("/loginFailure"))
            .logout(logout -> logout
                    .logoutSuccessUrl("/"));
                    // .csrf().disable();
        return http.build();
        //TODO https://www.baeldung.com/spring-security-5-oauth2-login
    }

    //TODO add ADMIN user
}