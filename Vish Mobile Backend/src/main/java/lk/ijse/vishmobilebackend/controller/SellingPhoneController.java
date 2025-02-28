package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.dto.SellingPhoneDTO;
import lk.ijse.vishmobilebackend.service.impl.SellingPhoneServiceImpl;
import lk.ijse.vishmobilebackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/sellingPhone")
@CrossOrigin
public class SellingPhoneController {

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
}
