package bar.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import bar.data.UserRepository;

@Configuration
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
        return http
        .authorizeRequests()
        .requestMatchers("/design", "/orders").access("hasRole('USER')")
        //resources below are available without registration
        .requestMatchers("/", "/**").access("permitAll()")
        //we will use and() every time when we create new configuration stage
        .and()
        .formLogin() //login conf. stage
        .loginPage("/login")
        //redirected path after login (if second param is true, redirection will be forced)
        .defaultSuccessUrl("/design", true)
        .and()
        .build();
    }

    //TODO add ADMIN user
}
