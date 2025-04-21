package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.dto.UserCartPhonesDTO;
import lk.ijse.vishmobilebackend.service.AddToCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    AddToCartService addToCartService;

    @RequestMapping("/getCartItemsById/{id}")
    public ResponseEntity<ResponseDTO> getCartPhonesByUser(@PathVariable Long id) {
        try {
            UserCartPhonesDTO cartPhones = addToCartService.getUserCartPhones(id);
            return ResponseEntity.ok(new ResponseDTO(200, "Cart phones fetched successfully", cartPhones));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to fetch cart phones", null));
        }
    }
}
