package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.dto.PhoneBuyingPriceDTO;
import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.service.PhoneBuyingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/phoneBuyingPrice")
@CrossOrigin
public class PhoneBuyingPriceController {
    @Autowired
    private PhoneBuyingPriceService phoneBuyingPriceService;

    @PostMapping("/savePhonePrice")
    public ResponseEntity<?> savePhoneBuyingPrice(@RequestBody PhoneBuyingPriceDTO phoneBuyingPriceDTO) {
        try {
            phoneBuyingPriceService.addPhoneBuyingPrice(phoneBuyingPriceDTO);
            return ResponseEntity.ok(new ResponseDTO(200, "Phone buying price saved successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to save phone buying price", null));
        }
    }
    @GetMapping("/getAllPhonePrices")
    public ResponseEntity<?> getAllPhoneBuyingPrices() {
        try {
            List<PhoneBuyingPriceDTO> phoneBuyingPrices = phoneBuyingPriceService.getAllPhoneBuyingPrices();
            return ResponseEntity.ok(new ResponseDTO(200, "Phone buying prices fetched successfully", phoneBuyingPrices));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to fetch phone buying prices", null));
        }
    }
    @DeleteMapping("/deletePhoneBuyingPrice/{id}")
    public ResponseEntity<?> deletePhoneBuyingPrice(@PathVariable int id) {
        try {
            phoneBuyingPriceService.deletePhoneBuyingPrice(id);
            return ResponseEntity.ok(new ResponseDTO(200, "Phone buying price deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to delete phone buying price", null));
        }
    }

}
