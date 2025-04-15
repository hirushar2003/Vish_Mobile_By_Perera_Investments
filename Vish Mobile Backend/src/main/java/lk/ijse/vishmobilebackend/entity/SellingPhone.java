package lk.ijse.vishmobilebackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "selling_phones")
public class SellingPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String model;
    private String capacity;
    private String color;
    private BigDecimal boughtPrice;
    private BigDecimal sellingPrice;
    private String batteryHealth;
    private long imei;

    @Transient
    private BigDecimal profit;

    @OneToMany(mappedBy = "phoneId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SellingPhonePhoto> photos;

    public BigDecimal getProfit() {
        return sellingPrice.subtract(boughtPrice);
    }
}
