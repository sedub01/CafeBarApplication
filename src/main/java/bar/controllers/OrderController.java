package bar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import bar.orders.CoffeeOrder;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("coffeeOrder")
public class OrderController {
    
    @GetMapping("/current")
    public String orderFrom(){
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid CoffeeOrder order, 
        Errors errors,
        SessionStatus sessionStatus){
        if (errors.hasErrors()){
            return "orderForm";
        }
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete(); 
        //there is garanty that session will be cleaned and ready for new order, when user will create coffee

        return "redirect:/";
    }
}