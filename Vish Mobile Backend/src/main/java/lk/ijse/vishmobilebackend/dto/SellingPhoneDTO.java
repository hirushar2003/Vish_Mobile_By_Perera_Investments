package lk.ijse.vishmobilebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SellingPhoneDTO {

    private int id;
    private String model;
    private String capacity;
    private String color;
    private BigDecimal boughtPrice;
    private BigDecimal sellingPrice;
    private BigDecimal profit;
    private int batteryHealth;
    private long imei;
    private long favouriteCount;
    private long cartCount;
    private List<String> photoUrls;
}
