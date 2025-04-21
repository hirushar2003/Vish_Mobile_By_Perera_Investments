package lk.ijse.vishmobilebackend.service;

import lk.ijse.vishmobilebackend.dto.SellingPhoneDTO;

import java.util.List;

public interface SellingPhoneService {
    void addSellingPhone(SellingPhoneDTO sellingPhoneDTO);
    void updateSellingPhone(SellingPhoneDTO sellingPhoneDTO);
    void deleteSellingPhone(int id);
    List<SellingPhoneDTO> getAllSellingPhones();
    Long getLastInsertedPhoneId();
    List<SellingPhoneDTO> getSellingPhonesWithPhotosByIds(List<Integer> ids);
}
