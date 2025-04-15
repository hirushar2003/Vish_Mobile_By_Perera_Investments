package lk.ijse.vishmobilebackend.service;

public interface CustomerPhonePricePredictionByFrameConditionService {
    double getAmountReducedByModelAndFrame(String model, String frameCondition);
}
