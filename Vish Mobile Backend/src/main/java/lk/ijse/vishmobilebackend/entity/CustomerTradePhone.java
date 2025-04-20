package lk.ijse.vishmobilebackend.entity;

import jakarta.persistence.*;
import lk.ijse.vishmobilebackend.model.ApprovalStatus;
import lk.ijse.vishmobilebackend.model.Box;
import lk.ijse.vishmobilebackend.model.WillingTo;
import lombok.*;

@Entity
@Table(name = "customer_iphones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerTradePhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private String storage;

    @Column(name = "battery_health")
    private String batteryHealth;

    @Column(name = "frame_condition")
    private String frameCondition;

    private String colour;

    @Enumerated(EnumType.STRING)
    @Column(name = "box")
    private Box box;

    @Enumerated(EnumType.STRING)
    @Column(name = "willing_to")
    private WillingTo willingTo;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval", nullable = false)
    private ApprovalStatus approval = ApprovalStatus.PENDING;

    @Column(name = "favourite_count")
    private Long favouriteCount = 0L;

    @Column(name = "cart_count")
    private Long cartCount = 0L;
}
