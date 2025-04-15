package lk.ijse.vishmobilebackend.service.impl;

import lk.ijse.vishmobilebackend.repo.CustomerPhonePricePredictionBatteryNegoRepo;
import lk.ijse.vishmobilebackend.service.CustomerPhonePricePredictionBatteryNegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerPhonePricePredictionBatteryNegoServiceImpl implements CustomerPhonePricePredictionBatteryNegoService {

    @Autowired
    private CustomerPhonePricePredictionBatteryNegoRepo customerPhonePricePredictionBatteryNegoRepo;

    @Override
    public double getAmountReducedByModelAndBattery(String model, String batteryHealthRange) {
        Double reducedAmount = customerPhonePricePredictionBatteryNegoRepo.findBatteryReductionAmount(model, batteryHealthRange);
        return reducedAmount != null ? reducedAmount : 0.0;
    }
}
