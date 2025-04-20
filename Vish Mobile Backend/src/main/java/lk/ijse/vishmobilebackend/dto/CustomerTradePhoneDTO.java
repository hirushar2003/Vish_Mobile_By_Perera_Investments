package lk.ijse.vishmobilebackend.dto;

import lk.ijse.vishmobilebackend.model.ApprovalStatus;
import lk.ijse.vishmobilebackend.model.WillingTo;
import lombok.*;
import lk.ijse.vishmobilebackend.model.Box;

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
}

