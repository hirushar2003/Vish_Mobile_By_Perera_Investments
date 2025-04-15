package lk.ijse.vishmobilebackend.service;

public interface CustomerPhonePricePredictionBatteryNegoService {
    double getAmountReducedByModelAndBattery(String model , String batteryHealthRange);
}
