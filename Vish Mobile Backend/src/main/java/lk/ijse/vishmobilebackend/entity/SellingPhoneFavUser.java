package lk.ijse.vishmobilebackend.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "selling_fav_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellingPhoneFavUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "selling_phone_id", nullable = false)
    private Integer sellingPhoneId;
}
