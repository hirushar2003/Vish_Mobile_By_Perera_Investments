package lk.ijse.vishmobilebackend.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.vishmobilebackend.entity.CustomerTradePhone;
import lk.ijse.vishmobilebackend.entity.SellingPhone;
import lk.ijse.vishmobilebackend.entity.SellingPhoneFavUser;
import lk.ijse.vishmobilebackend.entity.TradePhoneFavUser;
import lk.ijse.vishmobilebackend.repo.CustomerTradePhoneRepo;
import lk.ijse.vishmobilebackend.repo.SellingAddFavRepo;
import lk.ijse.vishmobilebackend.repo.SellingPhoneRepo;
import lk.ijse.vishmobilebackend.repo.TradeAddFavRepo;
import lk.ijse.vishmobilebackend.service.AddToFavService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddToFavServiceImpl implements AddToFavService {

    private final CustomerTradePhoneRepo tradePhoneRepo;
    private final SellingPhoneRepo sellingPhoneRepo;
    private final TradeAddFavRepo tradeAddFavRepo;
    private final SellingAddFavRepo sellingAddFavRepo;

    @Override
    public void addToFav(Long userId, Long phoneId, String tableName) {
        switch (tableName) {
            case "CUSTOMER_IPHONES" -> {
                boolean alreadyExists = tradeAddFavRepo.existsByUserIdAndTradePhoneId(userId, phoneId);
                if (!alreadyExists) {
                    tradeAddFavRepo.save(TradePhoneFavUser.builder()
                            .userId(userId)
                            .tradePhoneId(phoneId)
                            .build());

                    CustomerTradePhone phone = tradePhoneRepo.findById(phoneId)
                            .orElseThrow(() -> new EntityNotFoundException("Trade phone not found"));
                    phone.setFavouriteCount(phone.getFavouriteCount() + 1);
                    tradePhoneRepo.save(phone);
                }
            }

            case "SELLING_PHONES" -> {
                boolean alreadyExists = sellingAddFavRepo.existsByUserIdAndSellingPhoneId(userId, phoneId.intValue());
                if (!alreadyExists) {
                    sellingAddFavRepo.save(SellingPhoneFavUser.builder()
                            .userId(userId)
                            .sellingPhoneId(phoneId.intValue())
                            .build());

                    SellingPhone phone = sellingPhoneRepo.findById(phoneId.intValue())
                            .orElseThrow(() -> new EntityNotFoundException("Selling phone not found"));
                    phone.setFavouriteCount(phone.getFavouriteCount() + 1);
                    sellingPhoneRepo.save(phone);
                }
            }

            default -> throw new IllegalArgumentException("Invalid table name: " + tableName);
        }
    }

}
