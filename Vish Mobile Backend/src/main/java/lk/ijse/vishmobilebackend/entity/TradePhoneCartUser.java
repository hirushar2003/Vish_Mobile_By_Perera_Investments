package lk.ijse.vishmobilebackend.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "trade_cart_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TradePhoneCartUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "trade_phone_id", nullable = false)
    private Integer sellingPhoneId;
}
