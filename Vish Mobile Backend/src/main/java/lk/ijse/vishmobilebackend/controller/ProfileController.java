package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.dto.TradePhoneWithPhotosDTO;
import lk.ijse.vishmobilebackend.dto.UserCartPhonesDTO;
import lk.ijse.vishmobilebackend.dto.UserFavPhonesDTO;
import lk.ijse.vishmobilebackend.service.AddToCartService;
import lk.ijse.vishmobilebackend.service.AddToFavService;
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

    @Autowired
    private AddToFavService addToFavService;

    @Autowired
    private AddToCartService addToCartService;

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
    @GetMapping("/getFavPhonesByUser/{id}")
    public ResponseEntity<ResponseDTO> getFavPhonesByUser(@PathVariable Long id) {
        try {
            UserFavPhonesDTO favPhones = addToFavService.getUserFavPhones(id);
            return ResponseEntity.ok(new ResponseDTO(200, "Fav phones fetched successfully", favPhones));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to fetch fav phones", null));
        }
    }

    @GetMapping("/getCartPhonesByUser/{id}")
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
