package lk.ijse.vishmobilebackend.service;

import lk.ijse.vishmobilebackend.entity.PhonePhoto;
import lk.ijse.vishmobilebackend.entity.TradePhonePhoto;

import java.util.List;

public interface PhonePhotoService {
    void savePhonePhotos(List<PhonePhoto> photos);
    List<String> getPhotoUrlsByPhoneId(int phoneId);
    void saveTradePhonePhotos(List<TradePhonePhoto> photos);
    List<String> getTradePhotoUrlsByPhoneId(Long phoneId);
}
