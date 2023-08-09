package bar.utils;

import java.io.Serializable;

import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
@Table
public class CardDetails implements Serializable{

    private static final long serialVersionUID = 1L;

    public CardDetails() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
        message="Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    public void setData(CardDetails cardDetails) {
        ccNumber = cardDetails.getCcNumber();
        ccExpiration = cardDetails.getCcExpiration();
        ccCVV = cardDetails.getCcCVV();
    }
}
