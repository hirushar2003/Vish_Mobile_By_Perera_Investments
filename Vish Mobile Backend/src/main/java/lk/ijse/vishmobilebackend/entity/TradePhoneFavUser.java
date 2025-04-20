package lk.ijse.vishmobilebackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trade_fav_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradePhoneFavUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "trade_phone_id", nullable = false)
    private Long tradePhoneId;
}
