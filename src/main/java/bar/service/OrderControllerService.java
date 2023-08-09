package bar.service;

import org.springframework.stereotype.Service;

import bar.data.CardDetailsRepository;
import bar.security.User;
import bar.utils.CardDetails;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class OrderControllerService {

    private CardDetailsRepository cardDetailsRepo;

    public OrderControllerService(CardDetailsRepository cardDetailsRepo){
        this.cardDetailsRepo = cardDetailsRepo;
    }

    @Transactional
    public void saveCardDetails(User user, @Valid CardDetails cardDetails){
        //if we'll save cardDetails immediately, new row will be in database, because of new ID
        if (cardDetailsRepo.existsByCcNumber(cardDetails.getCcNumber())){
            Long cardId = user.getCardDetails().getId();
            CardDetails userCardDetails = cardDetailsRepo.findById(cardId).get();
            if (userCardDetails != null){ //Always true
                 //Change, not delete
                userCardDetails.setData(cardDetails);
                cardDetailsRepo.save(userCardDetails);
            }
        }
        else{
            user.setCardDetails(cardDetails);
            cardDetailsRepo.save(cardDetails);
        }
    }
}
