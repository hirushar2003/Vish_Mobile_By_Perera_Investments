package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.dto.TradePhoneWithPhotosDTO;
import lk.ijse.vishmobilebackend.model.ApprovalStatus;
import lk.ijse.vishmobilebackend.service.CustomerTradePhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    @GetMapping("/getPendingTradePhones")
    public ResponseEntity<?> getPendingTradePhones() {
        try {
            List<TradePhoneWithPhotosDTO> tradePhones = customerTradePhoneService.getPendingCustomerTradePhonesWithPhotos();
            return ResponseEntity.ok(tradePhones);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error fetching pending trade phones");
        }
    }
    @GetMapping("/getTradePhonesByStatus/{status}")
    public ResponseEntity<?> getTradePhonesByStatus(@PathVariable("status") String status) {
        try {
            ApprovalStatus approvalStatus = ApprovalStatus.valueOf(status.toUpperCase());
            List<TradePhoneWithPhotosDTO> tradePhones = customerTradePhoneService.getCustomerTradePhonesByStatusWithPhotos(approvalStatus);
            return ResponseEntity.ok(tradePhones);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid approval status: " + status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error fetching trade phones by status");
        }
    }
    @PutMapping("/changeStatusAndSellingPriceById/{id}/{sellingPrice}")
    public ResponseEntity<?> updateTradePhoneStatusAndSellingPrice(@PathVariable Long id, @PathVariable BigDecimal sellingPrice) {
        try {
            customerTradePhoneService.updateApprovalStatusAndSellingPrice(id, sellingPrice);
            return ResponseEntity.ok("Trade phone updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error updating trade phone status and price");
        }
    }
}
