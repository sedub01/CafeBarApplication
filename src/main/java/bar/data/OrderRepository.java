package bar.data;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bar.orders.CoffeeOrder;
import bar.security.User;

//Repository for storing orders
@Repository
public interface OrderRepository extends CrudRepository<CoffeeOrder, Long>{
    
    List<CoffeeOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
    Page<CoffeeOrder> findAll(Pageable pageable);
}
