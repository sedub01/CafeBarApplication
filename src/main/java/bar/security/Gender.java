package bar.security;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "genders")
public class Gender{
    private static long genderCount = 0;

    @Id
    private Long id;
    private String gender;

    public Gender() {}

    public Gender(String gender){
        this.id = genderCount++;
        this.gender = gender;
    }
}
