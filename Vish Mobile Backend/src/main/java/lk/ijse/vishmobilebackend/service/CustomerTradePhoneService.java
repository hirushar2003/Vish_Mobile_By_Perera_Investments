package lk.ijse.vishmobilebackend.service;

import lk.ijse.vishmobilebackend.dto.CustomerTradePhoneDTO;
import lk.ijse.vishmobilebackend.dto.TradePhoneWithPhotosDTO;
import lk.ijse.vishmobilebackend.model.ApprovalStatus;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerTradePhoneService {
    Long saveCustomerTradePhone(CustomerTradePhoneDTO customerTradePhoneDTO);
    List<CustomerTradePhoneDTO> getAllCustomerTradePhones();
    void deleteCustomerTradePhone(Long id);
    List<TradePhoneWithPhotosDTO> getCustomerTradePhonesWithPhotosByUserId(Long userId);
    List<TradePhoneWithPhotosDTO> getAdminTradePhonesWithPhotos();
    List<TradePhoneWithPhotosDTO> getAllApprovedCustomerTradePhonesWithPhotos();
    List<TradePhoneWithPhotosDTO> getTradePhonesWithPhotosByIds(List<Long> ids);
    List<TradePhoneWithPhotosDTO> getPendingCustomerTradePhonesWithPhotos();
    List<TradePhoneWithPhotosDTO> getCustomerTradePhonesByStatusWithPhotos(ApprovalStatus status);
    void updateApprovalStatusAndSellingPrice(Long id, BigDecimal sellingPrice);

}
