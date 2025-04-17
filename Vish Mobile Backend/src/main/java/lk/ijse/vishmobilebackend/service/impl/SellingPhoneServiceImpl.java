package lk.ijse.vishmobilebackend.service.impl;

import lk.ijse.vishmobilebackend.dto.SellingPhoneDTO;
import lk.ijse.vishmobilebackend.entity.SellingPhone;
import lk.ijse.vishmobilebackend.entity.PhonePhoto;
import lk.ijse.vishmobilebackend.repo.SellingPhoneRepo;
import lk.ijse.vishmobilebackend.repo.PhonePhotoRepo;
import lk.ijse.vishmobilebackend.service.SellingPhoneService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellingPhoneServiceImpl implements SellingPhoneService {

    @Autowired
    private SellingPhoneRepo sellingPhoneRepo;

    @Autowired
    private PhonePhotoRepo phonePhotoRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public void addSellingPhone(SellingPhoneDTO sellingPhoneDTO) {
        SellingPhone sellingPhone = modelMapper.map(sellingPhoneDTO, SellingPhone.class);
        sellingPhoneRepo.save(sellingPhone);

        if (sellingPhoneDTO.getPhotoUrls() != null) {
            List<PhonePhoto> photos = sellingPhoneDTO.getPhotoUrls().stream()
                    .map(url -> {
                        PhonePhoto photo = new PhonePhoto();
                        photo.setPhoneId(sellingPhone.getId());
                        photo.setPhotoUrl(url);
                        return photo;
                    }).collect(Collectors.toList());
            phonePhotoRepo.saveAll(photos);
        }

        messagingTemplate.convertAndSend("/topic/phones", sellingPhoneDTO);
    }

    @Override
    @Transactional
    public void updateSellingPhone(SellingPhoneDTO sellingPhoneDTO) {
        if (sellingPhoneRepo.existsById(sellingPhoneDTO.getId())) {
            SellingPhone sellingPhone = modelMapper.map(sellingPhoneDTO, SellingPhone.class);
            sellingPhoneRepo.save(sellingPhone);

            phonePhotoRepo.deleteByPhoneId(sellingPhone.getId());
            if (sellingPhoneDTO.getPhotoUrls() != null) {
                List<PhonePhoto> photos = sellingPhoneDTO.getPhotoUrls().stream()
                        .map(url -> {
                            PhonePhoto photo = new PhonePhoto();
                            photo.setPhoneId(sellingPhone.getId());
                            photo.setPhotoUrl(url);
                            return photo;
                        }).collect(Collectors.toList());
                phonePhotoRepo.saveAll(photos);
            }
        } else {
            throw new RuntimeException("Phone does not exist");
        }
    }

    @Override
    @Transactional
    public void deleteSellingPhone(int id) {
        phonePhotoRepo.deleteByPhoneId(id);
        sellingPhoneRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SellingPhoneDTO> getAllSellingPhones() {
        List<SellingPhone> phones = sellingPhoneRepo.findAll();
        return phones.stream().map(phone -> {
            SellingPhoneDTO dto = modelMapper.map(phone, SellingPhoneDTO.class);
            dto.setPhotoUrls(phonePhotoRepo.findByPhoneId(phone.getId())
                    .stream().map(PhonePhoto::getPhotoUrl).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }
    @Override
    public Long getLastInsertedPhoneId() {
        return sellingPhoneRepo.findLastInsertedId();
    }
}
