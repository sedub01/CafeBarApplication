package bar.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bar.orders.CoffeeOrder;

//Repository for storing orders
@Repository
public interface OrderRepository extends CrudRepository<CoffeeOrder, Long>{
    
    List<CoffeeOrder> findByDeliveryZip(String deliveryZip);
    List<CoffeeOrder> readOrdersByDeliveryZipAndPlacedAtBetween(
        String deliveryZip, Date startDate, Date endDate
    );
}
