package lk.ijse.vishmobilebackend.service;

import lk.ijse.vishmobilebackend.dto.CustomerTradePhoneDTO;
import lk.ijse.vishmobilebackend.entity.CustomerTradePhone;

import java.util.List;

public interface CustomerTradePhoneService {
    Long saveCustomerTradePhone(CustomerTradePhoneDTO customerTradePhoneDTO);
    List<CustomerTradePhoneDTO> getAllCustomerTradePhones();
    void deleteCustomerTradePhone(Long id);
}
