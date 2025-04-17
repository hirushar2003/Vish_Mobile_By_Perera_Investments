package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.dto.CustomerTradePhoneDTO;
import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.service.CustomerTradePhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customerPhoneTrade")
public class CustomerTradePhoneController {

    @Autowired
    private CustomerTradePhoneService customerTradePhoneService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCustomerTradePhone(@RequestBody CustomerTradePhoneDTO customerTradePhoneDTO) {
        try {
            Long generatedId = customerTradePhoneService.saveCustomerTradePhone(customerTradePhoneDTO);
            return ResponseEntity.ok(new ResponseDTO(200, "Trade phone saved and id fetched", generatedId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to save trade phone", null));
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomerTradePhone(@PathVariable Long id) {
        try {
            customerTradePhoneService.deleteCustomerTradePhone(id);
            return ResponseEntity.ok(new ResponseDTO(200, "Trade phone deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(404, "Trade phone not found", null));
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCustomerTradePhones() {
        try {
            List<CustomerTradePhoneDTO> customerTradePhones = customerTradePhoneService.getAllCustomerTradePhones();
            return ResponseEntity.ok(new ResponseDTO(200, "Customer trade phones fetched successfully", customerTradePhones));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to fetch customer trade phone", null));
        }
    }
}
