package lk.ijse.vishmobilebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "selling_phones")
@Data
public class SellingPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String model;
    private String capacity;
    private String color;
    private BigDecimal boughtPrice;
    private BigDecimal sellingPrice;

    @Transient
    private BigDecimal profit;

    @ElementCollection
    @CollectionTable(name = "selling_phone_photos", joinColumns = @JoinColumn(name = "phone_id"))
    @Column(name = "photo_url")
    private List<String> photoUrls; // Directly store as a List<String> in MySQL

    public BigDecimal getProfit() {
        return sellingPrice.subtract(boughtPrice);
    }
}
