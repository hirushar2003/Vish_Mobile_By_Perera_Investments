package lk.ijse.vishmobilebackend.service.impl;

import lk.ijse.vishmobilebackend.entity.SellingPhonePhoto;
import lk.ijse.vishmobilebackend.repo.SellingPhonePhotoRepo;
import lk.ijse.vishmobilebackend.service.SellingPhonePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellingPhonePhotoServiceImpl implements SellingPhonePhotoService {

    @Autowired
    private SellingPhonePhotoRepo sellingPhonePhotoRepo;

    @Override
    public void savePhonePhotos(List<SellingPhonePhoto> photos) {
        sellingPhonePhotoRepo.saveAll(photos); // Save all photo records in the database
    }
    @Override
    public List<String> getPhotoUrlsByPhoneId(int phoneId) {
        List<SellingPhonePhoto> photos = sellingPhonePhotoRepo.findByPhoneId(phoneId);
        return photos.stream().map(SellingPhonePhoto::getPhotoUrl).collect(Collectors.toList());
    }
}
