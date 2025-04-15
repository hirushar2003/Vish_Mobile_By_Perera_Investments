package lk.ijse.vishmobilebackend.service;

import lk.ijse.vishmobilebackend.entity.SellingPhonePhoto;

import java.util.List;

public interface SellingPhonePhotoService {
    // Method to save all phone photos
    void savePhonePhotos(List<SellingPhonePhoto> photos);
    List<String> getPhotoUrlsByPhoneId(int phoneId);
}
