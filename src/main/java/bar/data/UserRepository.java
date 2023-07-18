package bar.data;

import org.springframework.data.repository.CrudRepository;

import bar.security.User;

public interface UserRepository extends CrudRepository<User, Long>{

    User findByUsername(String username);    
}
