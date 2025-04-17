package lk.ijse.vishmobilebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "trade_phone_photos")
public class TradePhonePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trade_phone_id")
    private Long phoneId;

    @Column(name = "photo_url")
    private String photoUrl;
}
