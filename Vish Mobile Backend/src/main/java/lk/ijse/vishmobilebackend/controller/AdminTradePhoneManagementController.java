package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.dto.TradePhoneWithPhotosDTO;
import lk.ijse.vishmobilebackend.service.CustomerTradePhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/adminTradePhone")
public class AdminTradePhoneManagementController {

    @Autowired
    private CustomerTradePhoneService customerTradePhoneService;

    @GetMapping("/getAllTradePhonesWithPhotos")
    public ResponseEntity<?> getAllTradePhonesWithPhotos() {
        try {
            List<TradePhoneWithPhotosDTO> tradePhones = customerTradePhoneService.getAdminTradePhonesWithPhotos();
            return ResponseEntity.ok(tradePhones);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error fetching trade phones");
        }
    }
}
