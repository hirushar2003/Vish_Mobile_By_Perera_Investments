package lk.ijse.vishmobilebackend.service.impl;

import lk.ijse.vishmobilebackend.dto.CustomerTradePhoneDTO;
import lk.ijse.vishmobilebackend.dto.TradePhoneWithPhotosDTO;
import lk.ijse.vishmobilebackend.entity.CustomerTradePhone;
import lk.ijse.vishmobilebackend.model.ApprovalStatus;
import lk.ijse.vishmobilebackend.repo.CustomerTradePhoneRepo;
import lk.ijse.vishmobilebackend.service.CustomerTradePhoneService;
import lk.ijse.vishmobilebackend.service.PhonePhotoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerTradePhoneServiceImpl implements CustomerTradePhoneService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CustomerTradePhoneRepo customerTradePhoneRepo;

    @Autowired
    private PhonePhotoService phonePhotoService;

    @Override
    public Long saveCustomerTradePhone(CustomerTradePhoneDTO customerTradePhoneDTO) {
        CustomerTradePhone customerTradePhone = modelMapper.map(customerTradePhoneDTO, CustomerTradePhone.class);
        CustomerTradePhone saved = customerTradePhoneRepo.save(customerTradePhone);
        return saved.getId();
    }


    @Override
    public List<CustomerTradePhoneDTO> getAllCustomerTradePhones() {
        List<CustomerTradePhone> customerTradePhones = customerTradePhoneRepo.findAll();
        return customerTradePhones.stream()
                .map(customerTradePhone -> modelMapper.map(customerTradePhone, CustomerTradePhoneDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomerTradePhone(Long id) {
        if (customerTradePhoneRepo.existsById(id)) {
            customerTradePhoneRepo.deleteById(id);
        } else {
            throw new RuntimeException("Trade phone with " + id + " not found");
        }
    }


    @Override
    public List<TradePhoneWithPhotosDTO> getCustomerTradePhonesWithPhotosByUserId(Long userId) {
        return customerTradePhoneRepo.findByUserId(userId).stream()
                .map(phone -> {
                    TradePhoneWithPhotosDTO dto = modelMapper.map(phone, TradePhoneWithPhotosDTO.class);
                    dto.setPhotoUrls(phonePhotoService.getTradePhotoUrlsByPhoneId(phone.getId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TradePhoneWithPhotosDTO> getAdminTradePhonesWithPhotos() {
        return customerTradePhoneRepo.findAll().stream()
                .map(phone -> {
                    TradePhoneWithPhotosDTO dto = modelMapper.map(phone, TradePhoneWithPhotosDTO.class);
                    dto.setPhotoUrls(phonePhotoService.getTradePhotoUrlsByPhoneId(phone.getId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TradePhoneWithPhotosDTO> getAllApprovedCustomerTradePhonesWithPhotos() {
        return customerTradePhoneRepo.findApprovedPhones(ApprovalStatus.APPROVED).stream()
            .map(phone -> {
                TradePhoneWithPhotosDTO dto = modelMapper.map(phone, TradePhoneWithPhotosDTO.class);
                dto.setPhotoUrls(phonePhotoService.getTradePhotoUrlsByPhoneId(phone.getId()));
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<TradePhoneWithPhotosDTO> getTradePhonesWithPhotosByIds(List<Long> ids) {
        List<CustomerTradePhone> phones = customerTradePhoneRepo.findAllById(ids);
        return phones.stream()
                .map(phone -> {
                    TradePhoneWithPhotosDTO dto = modelMapper.map(phone, TradePhoneWithPhotosDTO.class);
                    List<String> photos = phonePhotoService.getTradePhotoUrlsByPhoneId(phone.getId());
                    dto.setPhotoUrls(photos);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
