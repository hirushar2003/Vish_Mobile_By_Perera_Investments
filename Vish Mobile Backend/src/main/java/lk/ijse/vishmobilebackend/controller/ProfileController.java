package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.dto.TradePhoneWithPhotosDTO;
import lk.ijse.vishmobilebackend.service.CustomerTradePhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/profileManager")
public class ProfileController {

    @Autowired
    CustomerTradePhoneService customerTradePhoneService;

    @GetMapping("/getTradingPhonesByUser/{id}")
    public ResponseEntity<ResponseDTO> getTradingPhonesByUser(@PathVariable Long id) {
        try {
            List<TradePhoneWithPhotosDTO> phonesWithPhotos = customerTradePhoneService.getCustomerTradePhonesWithPhotosByUserId(id);
            return ResponseEntity.ok(new ResponseDTO(200, "Trade phones with photos fetched successfully", phonesWithPhotos));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to fetch trade phones", null));
        }

    }
}
