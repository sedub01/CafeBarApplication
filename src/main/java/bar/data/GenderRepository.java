package bar.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bar.security.Gender;

@Repository
public interface GenderRepository extends CrudRepository<Gender, String>{
    List<Gender> findAll();
    Gender findById(int id);
}
