package lk.ijse.vishmobilebackend.dto;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerPhonePricePredictionBatteryNegoDTO {
    private int id;
    private String model;
    private String batteryHealthRange;
    private BigDecimal amountReduced;
}
