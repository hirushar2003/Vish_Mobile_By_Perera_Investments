package lk.ijse.vishmobilebackend.service;

import lk.ijse.vishmobilebackend.dto.CustomerTradePhoneDTO;
import lk.ijse.vishmobilebackend.dto.TradePhoneWithPhotosDTO;

import java.util.List;

public interface CustomerTradePhoneService {
    Long saveCustomerTradePhone(CustomerTradePhoneDTO customerTradePhoneDTO);
    List<CustomerTradePhoneDTO> getAllCustomerTradePhones();
    void deleteCustomerTradePhone(Long id);
    List<TradePhoneWithPhotosDTO> getCustomerTradePhonesWithPhotosByUserId(Long userId);
    List<TradePhoneWithPhotosDTO> getAdminTradePhonesWithPhotos();
    List<TradePhoneWithPhotosDTO> getAllApprovedCustomerTradePhonesWithPhotos();

}
