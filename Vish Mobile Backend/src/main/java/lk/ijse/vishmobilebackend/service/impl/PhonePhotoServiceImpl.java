package lk.ijse.vishmobilebackend.service.impl;

import lk.ijse.vishmobilebackend.entity.PhonePhoto;
import lk.ijse.vishmobilebackend.entity.TradePhonePhoto;
import lk.ijse.vishmobilebackend.repo.PhonePhotoRepo;
import lk.ijse.vishmobilebackend.repo.TradePhonePhotoRepo;
import lk.ijse.vishmobilebackend.service.PhonePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhonePhotoServiceImpl implements PhonePhotoService {

    @Autowired
    private PhonePhotoRepo phonePhotoRepo;

    @Autowired
    private TradePhonePhotoRepo tradePhonePhotoRepo;

    @Override
    public void savePhonePhotos(List<PhonePhoto> photos) {
        phonePhotoRepo.saveAll(photos);
    }
    @Override
    public List<String> getPhotoUrlsByPhoneId(int phoneId) {
        List<PhonePhoto> photos = phonePhotoRepo.findByPhoneId(phoneId);
        return photos.stream().map(PhonePhoto::getPhotoUrl).collect(Collectors.toList());
    }

    @Override
    public void saveTradePhonePhotos(List<TradePhonePhoto> photos) {
        tradePhonePhotoRepo.saveAll(photos);
    }

    @Override
    public List<String> getTradePhotoUrlsByPhoneId(int phoneId) {
        List<TradePhonePhoto> photos = tradePhonePhotoRepo.findByPhoneId(phoneId);
        return photos.stream().map(TradePhonePhoto::getPhotoUrl).collect(Collectors.toList());
    }
}
