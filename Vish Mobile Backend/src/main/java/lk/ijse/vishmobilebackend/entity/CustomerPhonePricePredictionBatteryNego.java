package lk.ijse.vishmobilebackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "battery_nego")
public class CustomerPhonePricePredictionBatteryNego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    @Column(name = "battery_health_range")
    private String batteryHealthRange;

    @Column(name = "amount_reduced")
    private BigDecimal amountReduced;
}

