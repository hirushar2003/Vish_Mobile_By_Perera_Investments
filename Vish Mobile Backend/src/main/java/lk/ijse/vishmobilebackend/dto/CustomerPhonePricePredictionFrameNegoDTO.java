package lk.ijse.vishmobilebackend.dto;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPhonePricePredictionFrameNegoDTO {

    private Integer id;
    private String model;
    private String frameCondition;
    private BigDecimal amountReduced;
}
