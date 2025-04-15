package lk.ijse.vishmobilebackend.service.impl;

import lk.ijse.vishmobilebackend.dto.PhoneBuyingPriceDTO;
import lk.ijse.vishmobilebackend.entity.PhoneBuyingPrice;
import lk.ijse.vishmobilebackend.repo.PhoneBuyingPriceRepo;
import lk.ijse.vishmobilebackend.service.PhoneBuyingPriceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneBuyingPriceServiceImpl implements PhoneBuyingPriceService {
    @Autowired
    PhoneBuyingPriceRepo phoneBuyingPriceRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addPhoneBuyingPrice(PhoneBuyingPriceDTO phoneBuyingPriceDTO) {
        PhoneBuyingPrice phoneBuyingPrice = modelMapper.map(phoneBuyingPriceDTO, PhoneBuyingPrice.class);
        phoneBuyingPriceRepo.save(phoneBuyingPrice);
    }

    @Override
    public List<PhoneBuyingPriceDTO> getAllPhoneBuyingPrices() {
        List<PhoneBuyingPrice> phoneBuyingPrices = phoneBuyingPriceRepo.findAll();
        return phoneBuyingPrices.stream()
                .map(phone -> modelMapper.map(phone, PhoneBuyingPriceDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public void deletePhoneBuyingPrice(int id) {
        if (phoneBuyingPriceRepo.existsById(id)) {
            phoneBuyingPriceRepo.deleteById(id);
        } else {
            throw new RuntimeException("Phone buying price with ID " + id + " not found");
        }
    }
    @Override
    public double getPhonePriceByModelAndStorage(String model, String storage) {
        Double price = phoneBuyingPriceRepo.findPriceByModelAndStorage(model, storage);
        if (price == null) {
            throw new RuntimeException("Price not found for model: " + model + " and storage: " + storage);
        }
        return price;
    }
}
