package bar.orders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.CreditCardNumber;

import bar.coffee.Coffee;
import bar.security.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "Coffee_Orders")
public class CoffeeOrder implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") //If name == variable name, @Column is redundant
    private Long id;
    private Date placedAt = new Date();

    @NotBlank(message="Delivery name is required")
    private String deliveryName;
    @NotBlank(message="City is required")
    private String deliveryCity;
    @NotBlank(message="Automaton code is required")
    private String automatonCode;
    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
        message="Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    @ManyToOne
    private User user;

    //One order (this class) contains many coffees,
    //one coffee (class in List) relates only to one order
    //CascadeType.ALL means that after order deletion all related coffees will delete too
    @OneToMany(cascade = CascadeType.ALL)
    private List<Coffee> coffees = new ArrayList<>();

    public void addCoffee(Coffee coffee){
        coffees.add(coffee);
    }

    public void autofillTextFields(User user) {
        if (automatonCode == null || automatonCode.isEmpty()){
            automatonCode = user.getAutomatonCode();
        }
        if (deliveryCity == null || deliveryCity.isEmpty()){
            deliveryCity = user.getCity();
        }
    }
}
