package bar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import bar.data.OrderRepository;
import bar.data.UserRepository;
import bar.orders.CoffeeOrder;
import bar.security.User;
import bar.service.OrderControllerService;
import bar.utils.OrderProps;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("coffeeOrder")
public class OrderController {
    
    private OrderRepository orderRepo;
    private OrderControllerService orderControllerService;
    private UserRepository userRepo;
    private OrderProps props;

    @Autowired
    public OrderController(OrderRepository orderRepo,
        OrderControllerService orderControllerSerivce,
        UserRepository userRepo,
        OrderProps props) {
        this.orderRepo = orderRepo;
        this.orderControllerService = orderControllerSerivce;
        this.userRepo = userRepo;
        this.props = props;
    }

    @GetMapping("/current")
    public String orderFrom(@AuthenticationPrincipal User user,
        @ModelAttribute CoffeeOrder order){
        order.autofillTextFields(user);

        return "orderForm";
    }

    @GetMapping
    public String ordersForUser(
        @AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, props.getPageSize());

        var orders = orderRepo.findByUserOrderByPlacedAtDesc(user, pageable);
        model.addAttribute("orders", orders);

        return "orderList";
    }

    @PostMapping
    public String processOrder(@Valid CoffeeOrder order,
        Errors errors,
        SessionStatus sessionStatus,
        @AuthenticationPrincipal User user){
        if (errors.hasErrors()){
            return "orderForm";
        }
        var cardDetails = order.getCardDetails();
        //First, we set user, only then we save order!
        if (!user.hasCardDetails())
            user.setCardDetails(cardDetails);
        order.setUser(user);

        orderControllerService.saveCardDetails(user, cardDetails);
        userRepo.save(user);
        orderRepo.save(order);
        sessionStatus.setComplete(); 
        log.info("Order submitted: {}", order);
        //there is garanty that session will be cleaned
        //and ready for new order, when user will create coffee

        return "redirect:/orders";
    }
}
