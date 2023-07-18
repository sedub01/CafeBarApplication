package bar.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import bar.data.OrderRepository;

@Service
public class OrderAdminService {
    
    private OrderRepository orderRepo;

    public OrderAdminService(OrderRepository orderRepo){
        this.orderRepo = orderRepo;
    }

    //if role != ADMIN, method won't invoke
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders(){
        orderRepo.deleteAll();
    }
}
