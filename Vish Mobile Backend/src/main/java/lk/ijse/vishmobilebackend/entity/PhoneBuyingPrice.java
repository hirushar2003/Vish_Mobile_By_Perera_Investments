package lk.ijse.vishmobilebackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "iphone_buying_prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneBuyingPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String model;

    @Column(nullable = false, length = 10)
    private String storage;

    @Column(nullable = false, length = 50)
    private String color;

    @Column(nullable = false, length = 7)
    private String colorCode;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}

