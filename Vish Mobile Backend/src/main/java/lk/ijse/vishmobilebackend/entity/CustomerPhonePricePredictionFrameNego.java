package lk.ijse.vishmobilebackend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "frame_nego")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPhonePricePredictionFrameNego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String model;

    @Column(name = "frame_condition", nullable = false, length = 30)
    private String frameCondition;

    @Column(name = "amount_reduced", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountReduced;
}
