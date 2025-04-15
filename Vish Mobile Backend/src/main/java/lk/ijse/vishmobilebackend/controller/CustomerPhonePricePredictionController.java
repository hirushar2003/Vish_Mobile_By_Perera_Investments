package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.service.CustomerPhonePricePredictionBatteryNegoService;
import lk.ijse.vishmobilebackend.service.CustomerPhonePricePredictionByFrameConditionService;
import lk.ijse.vishmobilebackend.service.PhoneBuyingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customerPhonePricePrediction")
public class CustomerPhonePricePredictionController {
    @Autowired
    private PhoneBuyingPriceService phoneBuyingPriceService;

    @Autowired
    private CustomerPhonePricePredictionBatteryNegoService customerPhonePricePredictionBatteryNegoService;

    @Autowired
    private CustomerPhonePricePredictionByFrameConditionService customerPhonePricePredictionByFrameConditionService;

    @GetMapping("/getPhonePrice")
    public ResponseEntity<?> getPhonePriceAccordingToModelAndStorage(@RequestParam String model , @RequestParam String storage) {
        try {
            double phoneBuyingPrice = phoneBuyingPriceService.getPhonePriceByModelAndStorage(model, storage);
            return ResponseEntity.ok(new ResponseDTO(200, "Phone buying price fetched successfully", phoneBuyingPrice));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Failed to fetch phone buying price", null));
        }
    }

    @GetMapping("/getBatteryNegotiation")
    public ResponseEntity<?> getBatteryNegotiationPriceByBatteryHealth(@RequestParam String model, @RequestParam String batteryHealth) {
        try {
            double batteryNegotiationPrice = customerPhonePricePredictionBatteryNegoService.getAmountReducedByModelAndBattery(model, batteryHealth);
            return ResponseEntity.ok(new ResponseDTO(200 , "Price negotiation according to battery fetched" , batteryNegotiationPrice));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching battery negotiation price");
        }
    }

    @GetMapping("/getFrameNegotiation")
    public ResponseEntity<?> getCustomerPhonePricePredictionByFrameCondition(@RequestParam String model, @RequestParam String frameCondition) {
        try {
            double frameNegotiationPrice = customerPhonePricePredictionByFrameConditionService.getAmountReducedByModelAndFrame(model, frameCondition);
            return ResponseEntity.ok(new ResponseDTO(200 , "Price negotiation according to frame fetched" , frameNegotiationPrice));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching frame negotiation price");
        }
    }
}
