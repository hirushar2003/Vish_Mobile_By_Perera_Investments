package lk.ijse.vishmobilebackend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneBuyingPriceDTO {
    private Integer id;
    private String model;
    private String storage;
    private String color;
    private String colorCode;
    private BigDecimal price;
}

