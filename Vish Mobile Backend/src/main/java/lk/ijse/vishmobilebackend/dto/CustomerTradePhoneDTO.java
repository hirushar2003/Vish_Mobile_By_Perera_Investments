package lk.ijse.vishmobilebackend.dto;

import lk.ijse.vishmobilebackend.model.ApprovalStatus;
import lk.ijse.vishmobilebackend.model.WillingTo;
import lombok.*;
import lk.ijse.vishmobilebackend.model.Box;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerTradePhoneDTO {
    private int id;
    private String model;
    private String storage;
    private String batteryHealth;
    private String frameCondition;
    private String colour;
    private WillingTo willingTo;
    private Box box;
    private Long userId;
    private ApprovalStatus approval;
    private BigDecimal boughtPrice;
    private BigDecimal sellingPrice;
    private long favouriteCount;
    private long cartCount;
}

