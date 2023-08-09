package bar.data;

import org.springframework.data.repository.CrudRepository;

import bar.utils.CardDetails;

public interface CardDetailsRepository extends CrudRepository<CardDetails, Long>{

    public boolean existsByCcNumber(String ccNumber);
}
