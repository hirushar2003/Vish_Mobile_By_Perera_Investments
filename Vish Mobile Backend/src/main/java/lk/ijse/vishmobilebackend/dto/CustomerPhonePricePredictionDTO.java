package lk.ijse.vishmobilebackend.dto;

import lk.ijse.vishmobilebackend.model.WillingTo;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPhonePricePredictionDTO {
    private int id;
    private String model;
    private String storage;
    private String batteryHealth;
    private String frameCondition;
    private String colour;
    private WillingTo willingTo;
    private Long userId;
}
