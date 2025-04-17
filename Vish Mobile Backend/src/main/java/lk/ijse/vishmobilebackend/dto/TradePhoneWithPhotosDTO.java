package lk.ijse.vishmobilebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TradePhoneWithPhotosDTO {
    private Long id;
    private String batteryHealth;
    private String box;
    private String colour;
    private String frameCondition;
    private String model;
    private String storage;
    private Long userId;
    private String willingTo;
    private List<String> photoUrls;
}
