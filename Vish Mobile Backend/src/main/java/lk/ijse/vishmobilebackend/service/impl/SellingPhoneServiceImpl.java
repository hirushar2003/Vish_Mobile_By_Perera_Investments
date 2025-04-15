package lk.ijse.vishmobilebackend.service.impl;

import lk.ijse.vishmobilebackend.dto.SellingPhoneDTO;
import lk.ijse.vishmobilebackend.entity.SellingPhone;
import lk.ijse.vishmobilebackend.entity.SellingPhonePhoto;
import lk.ijse.vishmobilebackend.repo.SellingPhoneRepo;
import lk.ijse.vishmobilebackend.repo.SellingPhonePhotoRepo;
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
    private SellingPhonePhotoRepo sellingPhonePhotoRepo;

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
            List<SellingPhonePhoto> photos = sellingPhoneDTO.getPhotoUrls().stream()
                    .map(url -> {
                        SellingPhonePhoto photo = new SellingPhonePhoto();
                        photo.setPhoneId(sellingPhone.getId());
                        photo.setPhotoUrl(url);
                        return photo;
                    }).collect(Collectors.toList());
            sellingPhonePhotoRepo.saveAll(photos);
        }

        messagingTemplate.convertAndSend("/topic/phones", sellingPhoneDTO);
    }

    @Override
    @Transactional
    public void updateSellingPhone(SellingPhoneDTO sellingPhoneDTO) {
        if (sellingPhoneRepo.existsById(sellingPhoneDTO.getId())) {
            SellingPhone sellingPhone = modelMapper.map(sellingPhoneDTO, SellingPhone.class);
            sellingPhoneRepo.save(sellingPhone);

            sellingPhonePhotoRepo.deleteByPhoneId(sellingPhone.getId());
            if (sellingPhoneDTO.getPhotoUrls() != null) {
                List<SellingPhonePhoto> photos = sellingPhoneDTO.getPhotoUrls().stream()
                        .map(url -> {
                            SellingPhonePhoto photo = new SellingPhonePhoto();
                            photo.setPhoneId(sellingPhone.getId());
                            photo.setPhotoUrl(url);
                            return photo;
                        }).collect(Collectors.toList());
                sellingPhonePhotoRepo.saveAll(photos);
            }
        } else {
            throw new RuntimeException("Phone does not exist");
        }
    }

    @Override
    @Transactional
    public void deleteSellingPhone(int id) {
        sellingPhonePhotoRepo.deleteByPhoneId(id);
        sellingPhoneRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SellingPhoneDTO> getAllSellingPhones() {
        List<SellingPhone> phones = sellingPhoneRepo.findAll();
        return phones.stream().map(phone -> {
            SellingPhoneDTO dto = modelMapper.map(phone, SellingPhoneDTO.class);
            dto.setPhotoUrls(sellingPhonePhotoRepo.findByPhoneId(phone.getId())
                    .stream().map(SellingPhonePhoto::getPhotoUrl).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }
    @Override
    public Long getLastInsertedPhoneId() {
        return sellingPhoneRepo.findLastInsertedId();
    }
}
