package bar.orders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bar.coffee.Coffee;
import bar.security.User;
import bar.utils.CardDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "coffee_orders")
public class CoffeeOrder implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") //If name == variable name, @Column is redundant
    private Long id;
    private Date placedAt = new Date();

    @NotBlank(message="Delivery name is required")
    private String deliveryName;
    @NotBlank(message="Automaton code is required")
    private String automatonCode;

    @ManyToOne
    private User user;
    @Valid
    @ManyToOne
    private CardDetails cardDetails;

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
        if (cardDetails == null){
            cardDetails = user.getCardDetails();
        }
    }
}
