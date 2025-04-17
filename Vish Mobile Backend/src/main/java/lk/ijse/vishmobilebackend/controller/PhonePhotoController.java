package lk.ijse.vishmobilebackend.controller;



import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.entity.PhonePhoto;
import lk.ijse.vishmobilebackend.entity.TradePhonePhoto;
import lk.ijse.vishmobilebackend.service.PhonePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sellingPhonePhoto")
public class PhonePhotoController {

    @Autowired
    private PhonePhotoService phonePhotoService;



//    ------------------------------------SellingPhonePhotos------------------------------

    @PostMapping("/save")
    public ResponseEntity<?> savePhonePhotos(@RequestBody List<PhonePhoto> photos) {
        try {
            phonePhotoService.savePhonePhotos(photos);
            return ResponseEntity.ok(new ResponseDTO(200, "Photos saved successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to save photos", null));
        }
    }

    @GetMapping("/getPhotoByPhoneId/{phoneId}")
    public ResponseEntity<?> getPhotoUrlsByPhoneId(@PathVariable int phoneId) {
        try {
            List<String> photoUrls = phonePhotoService.getPhotoUrlsByPhoneId(phoneId);
            return ResponseEntity.ok(new ResponseDTO(200, "Photos retrieved successfully", photoUrls));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to retrieve photos", null));
        }
    }


//    -----------------------------TradePhonePhoto----------------------------------

    @PostMapping("/savePhoto")
    public ResponseEntity<?> saveTradePhonePhotos(@RequestBody List<TradePhonePhoto> photos) {
        try {
            phonePhotoService.saveTradePhonePhotos(photos);
            return ResponseEntity.ok(new ResponseDTO(200, "Photos saved successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to save photos", null));
        }
    }

    @GetMapping("/getTradePhotoByPhoneId/{phoneId}")
    public ResponseEntity<?> getTradePhotoUrlsByPhoneId(@PathVariable int phoneId) {
        try {
            List<String> photoUrls = phonePhotoService.getTradePhotoUrlsByPhoneId(phoneId);
            return ResponseEntity.ok(new ResponseDTO(200, "Photos retrieved successfully", photoUrls));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to retrieve photos", null));
        }
    }
}
