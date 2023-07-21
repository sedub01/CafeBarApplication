package bar.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import bar.utils.Role;
import lombok.Data;

@Data
public class RegistrationForm {

    private final String username;
    private final String password;
    private final String fullname;
    private final String city;
    private final String automatonCode;
    private final String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
            username, passwordEncoder.encode(password),
            fullname, city, automatonCode, phoneNumber, Role.USER
        );
    }
    
}
