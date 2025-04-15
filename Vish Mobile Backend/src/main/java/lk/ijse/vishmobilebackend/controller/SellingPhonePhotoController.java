package lk.ijse.vishmobilebackend.controller;



import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.entity.SellingPhonePhoto;
import lk.ijse.vishmobilebackend.service.SellingPhonePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sellingPhonePhoto")
public class SellingPhonePhotoController {

    @Autowired
    private SellingPhonePhotoService sellingPhonePhotoService;

    @PostMapping("/save")
    public ResponseEntity<?> savePhonePhotos(@RequestBody List<SellingPhonePhoto> photos) {
        try {
            sellingPhonePhotoService.savePhonePhotos(photos);
            return ResponseEntity.ok(new ResponseDTO(200, "Photos saved successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to save photos", null));
        }
    }

    @GetMapping("/getPhotoByPhoneId/{phoneId}")
    public ResponseEntity<?> getPhotoUrlsByPhoneId(@PathVariable int phoneId) {
        try {
            List<String> photoUrls = sellingPhonePhotoService.getPhotoUrlsByPhoneId(phoneId);
            return ResponseEntity.ok(new ResponseDTO(200, "Photos retrieved successfully", photoUrls));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to retrieve photos", null));
        }
    }

}
