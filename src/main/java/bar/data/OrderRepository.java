package bar.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bar.orders.CoffeeOrder;

//Repository for storing orders
@Repository
public interface OrderRepository extends CrudRepository<CoffeeOrder, Long>{
    
}
