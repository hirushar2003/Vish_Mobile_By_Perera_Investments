package lk.ijse.vishmobilebackend.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.vishmobilebackend.dto.SellingPhoneDTO;
import lk.ijse.vishmobilebackend.dto.TradePhoneWithPhotosDTO;
import lk.ijse.vishmobilebackend.dto.UserCartPhonesDTO;
import lk.ijse.vishmobilebackend.entity.CustomerTradePhone;
import lk.ijse.vishmobilebackend.entity.SellingPhone;
import lk.ijse.vishmobilebackend.entity.SellingPhoneCartUser;
import lk.ijse.vishmobilebackend.entity.TradePhoneCartUser;
import lk.ijse.vishmobilebackend.repo.CustomerTradePhoneRepo;
import lk.ijse.vishmobilebackend.repo.SellingPhoneRepo;
import lk.ijse.vishmobilebackend.repo.SellingAddCartRepo;
import lk.ijse.vishmobilebackend.repo.TradeAddCartRepo;
import lk.ijse.vishmobilebackend.service.AddToCartService;
import lk.ijse.vishmobilebackend.service.CustomerTradePhoneService;
import lk.ijse.vishmobilebackend.service.SellingPhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddToCartServiceImpl implements AddToCartService {

    private final CustomerTradePhoneRepo tradePhoneRepo;
    private final SellingPhoneRepo sellingPhoneRepo;
    private final TradeAddCartRepo tradeAddCartRepo;
    private final SellingAddCartRepo sellingAddCartRepo;

    @Autowired
    private CustomerTradePhoneService customerTradePhoneService;

    @Autowired
    private SellingPhoneService sellingPhoneService;

    @Override
    public void addToCart(Long userId, Long phoneId, String tableName) {
        switch (tableName) {
            case "CUSTOMER_IPHONES" -> {
                tradeAddCartRepo.save(TradePhoneCartUser.builder()
                        .userId(userId)
                        .tradePhoneId(phoneId)
                        .build());

                CustomerTradePhone phone = tradePhoneRepo.findById(phoneId)
                        .orElseThrow(() -> new EntityNotFoundException("Trade phone not found"));

                phone.setCartCount(phone.getCartCount() + 1);
                tradePhoneRepo.save(phone);
            }

            case "SELLING_PHONES" -> {
                sellingAddCartRepo.save(SellingPhoneCartUser.builder()
                        .userId(userId)
                        .sellingPhoneId(phoneId.intValue())
                        .build());

                SellingPhone phone = sellingPhoneRepo.findById(phoneId.intValue())
                        .orElseThrow(() -> new EntityNotFoundException("Selling phone not found"));

                phone.setCartCount(phone.getCartCount() + 1);
                sellingPhoneRepo.save(phone);
            }

            default -> throw new IllegalArgumentException("Invalid table name: " + tableName);
        }
    }


    @Override
    public UserCartPhonesDTO getUserCartPhones(Long userId) {
        // Get trade cart phone IDs
        List<Long> tradePhoneIds = tradeAddCartRepo.findByUserId(userId)
                .stream().map(TradePhoneCartUser::getTradePhoneId).toList();

        // Get selling cart phone IDs
        List<Integer> sellingPhoneIds = sellingAddCartRepo.findByUserId(userId)
                .stream().map(SellingPhoneCartUser::getSellingPhoneId).toList();

        // Fetch phones with photos
        List<TradePhoneWithPhotosDTO> tradePhones =
                customerTradePhoneService.getTradePhonesWithPhotosByIds(tradePhoneIds);

        List<SellingPhoneDTO> sellingPhones =
                sellingPhoneService.getSellingPhonesWithPhotosByIds(sellingPhoneIds);

        return new UserCartPhonesDTO(tradePhones, sellingPhones);
    }

}
