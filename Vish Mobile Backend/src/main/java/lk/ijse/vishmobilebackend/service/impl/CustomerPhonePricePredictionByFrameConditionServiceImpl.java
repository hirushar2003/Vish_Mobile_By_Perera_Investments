package lk.ijse.vishmobilebackend.service.impl;

import lk.ijse.vishmobilebackend.repo.CustomerPhonePricePredictionByFrameConditionRepo;
import lk.ijse.vishmobilebackend.service.CustomerPhonePricePredictionByFrameConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerPhonePricePredictionByFrameConditionServiceImpl implements CustomerPhonePricePredictionByFrameConditionService {
    @Autowired
    private CustomerPhonePricePredictionByFrameConditionRepo customerPhonePricePredictionByFrameConditionRepo;

    @Override
    public double getAmountReducedByModelAndFrame(String model, String frameCondition) {
        Double reducedAmountByFrame = customerPhonePricePredictionByFrameConditionRepo.findFrameReductionAmount(model, frameCondition);
        return reducedAmountByFrame != null ? reducedAmountByFrame : 0;
    }
}
