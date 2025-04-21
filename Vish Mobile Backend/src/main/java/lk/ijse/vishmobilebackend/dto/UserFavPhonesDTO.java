package lk.ijse.vishmobilebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFavPhonesDTO {
    private List<TradePhoneWithPhotosDTO> tradePhones;
    private List<SellingPhoneDTO> sellingPhones;
}

