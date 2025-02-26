package lk.ijse.vishmobilebackend.service.impl;

import lk.ijse.vishmobilebackend.dto.SellingPhoneDTO;
import lk.ijse.vishmobilebackend.entity.SellingPhone;
import lk.ijse.vishmobilebackend.entity.SellingPhonePhoto;
import lk.ijse.vishmobilebackend.repo.SellingPhonePhotoRepo;
import lk.ijse.vishmobilebackend.repo.SellingPhoneRepo;
import lk.ijse.vishmobilebackend.service.SellingPhoneService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellingPhoneServiceImpl implements SellingPhoneService {

    @Autowired
    private SellingPhoneRepo sellingPhoneRepo;

    @Autowired
    private SellingPhonePhotoRepo sellingPhonePhotoRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addSellingPhone(SellingPhoneDTO sellingPhoneDTO) {
        // Save the phone details in the selling_phones table
        SellingPhone sellingPhone = modelMapper.map(sellingPhoneDTO, SellingPhone.class);
        sellingPhone = sellingPhoneRepo.save(sellingPhone);

        // Save the photos in the selling_phone_photos table
        if (sellingPhoneDTO.getPhotoUrls() != null) {
            for (String photoUrl : sellingPhoneDTO.getPhotoUrls()) {
                SellingPhonePhoto sellingPhonePhoto = new SellingPhonePhoto();
                sellingPhonePhoto.setPhoneId(sellingPhone.getId());
                sellingPhonePhoto.setPhotoUrl(photoUrl);
                sellingPhonePhotoRepo.save(sellingPhonePhoto);
            }
        }
    }

    @Override
    public void updateSellingPhone(SellingPhoneDTO sellingPhoneDTO) {
        if (sellingPhoneRepo.existsById(sellingPhoneDTO.getId())) {
            // Update phone details in the selling_phones table
            SellingPhone sellingPhone = modelMapper.map(sellingPhoneDTO, SellingPhone.class);
            sellingPhoneRepo.save(sellingPhone);

            // Delete old photos before updating them
            sellingPhonePhotoRepo.deleteByPhoneId(sellingPhoneDTO.getId());

            // Save the new photos in the selling_phone_photos table
            if (sellingPhoneDTO.getPhotoUrls() != null) {
                for (String photoUrl : sellingPhoneDTO.getPhotoUrls()) {
                    SellingPhonePhoto sellingPhonePhoto = new SellingPhonePhoto();
                    sellingPhonePhoto.setPhoneId(sellingPhone.getId());
                    sellingPhonePhoto.setPhotoUrl(photoUrl);
                    sellingPhonePhotoRepo.save(sellingPhonePhoto);
                }
            }
        } else {
            throw new RuntimeException("Phone does not exist");
        }
    }

    @Override
    public void deleteSellingPhone(int id) {
        // Delete photos associated with the phone
        sellingPhonePhotoRepo.deleteByPhoneId(id);
        sellingPhoneRepo.deleteById(id);
    }

    @Override
    public List<SellingPhoneDTO> getAllSellingPhones() {
        List<SellingPhone> sellingPhones = sellingPhoneRepo.findAll();
        return modelMapper.map(sellingPhones, new TypeToken<List<SellingPhoneDTO>>() {}.getType());
    }
}
