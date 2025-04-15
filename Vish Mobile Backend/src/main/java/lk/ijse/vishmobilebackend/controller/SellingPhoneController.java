package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.dto.SellingPhoneDTO;
import lk.ijse.vishmobilebackend.service.impl.SellingPhoneServiceImpl;
import lk.ijse.vishmobilebackend.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin
@RequestMapping("api/v1/sellingPhone")
public class SellingPhoneController {
    private static final Logger log = LoggerFactory.getLogger(SellingPhoneController.class);

    @Autowired
    private SellingPhoneServiceImpl sellingPhoneService;

    @PostMapping("save")
    public ResponseUtil savePhone(@RequestBody SellingPhoneDTO sellingPhoneDTO) {
        sellingPhoneService.addSellingPhone(sellingPhoneDTO);
        return new ResponseUtil(201, "Phone Saved", null);
    }

    @PutMapping("update")
    public ResponseUtil updatePhone(@RequestBody SellingPhoneDTO sellingPhoneDTO) {
        sellingPhoneService.updateSellingPhone(sellingPhoneDTO);
        return new ResponseUtil(200, "Phone Updated", null);
    }

    @DeleteMapping("delete/{id}")
    public ResponseUtil deletePhone(@PathVariable("id") int id) {
        sellingPhoneService.deleteSellingPhone(id);
        return new ResponseUtil(200, "Phone Deleted", null);
    }

    @GetMapping("getAll")
    public ResponseUtil getAllPhones() {
        return new ResponseUtil(200, "Phone List", sellingPhoneService.getAllSellingPhones());
    }

    @GetMapping("/lastId")
    public ResponseEntity<?> getLastInsertedPhoneId() {
        Long lastId = sellingPhoneService.getLastInsertedPhoneId();
        if (lastId != null) {
            return ResponseEntity.ok(Collections.singletonMap("id", lastId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No phones found");
    }
}
