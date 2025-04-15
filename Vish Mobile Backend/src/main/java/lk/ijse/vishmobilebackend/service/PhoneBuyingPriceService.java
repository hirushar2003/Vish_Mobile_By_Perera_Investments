package lk.ijse.vishmobilebackend.service;

import lk.ijse.vishmobilebackend.dto.PhoneBuyingPriceDTO;

import java.util.List;

public interface PhoneBuyingPriceService {
    void addPhoneBuyingPrice(PhoneBuyingPriceDTO phoneBuyingPriceDTO);
    List<PhoneBuyingPriceDTO> getAllPhoneBuyingPrices();
    void deletePhoneBuyingPrice(int id);
    double getPhonePriceByModelAndStorage(String model , String storage);
}
