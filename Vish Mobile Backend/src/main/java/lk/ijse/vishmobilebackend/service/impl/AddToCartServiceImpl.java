package lk.ijse.vishmobilebackend.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.vishmobilebackend.entity.CustomerTradePhone;
import lk.ijse.vishmobilebackend.entity.SellingPhone;
import lk.ijse.vishmobilebackend.entity.SellingPhoneCartUser;
import lk.ijse.vishmobilebackend.entity.TradePhoneCartUser;
import lk.ijse.vishmobilebackend.repo.CustomerTradePhoneRepo;
import lk.ijse.vishmobilebackend.repo.SellingPhoneRepo;
import lk.ijse.vishmobilebackend.repo.SellingAddCartRepo;
import lk.ijse.vishmobilebackend.repo.TradeAddCartRepo;
import lk.ijse.vishmobilebackend.service.AddToCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddToCartServiceImpl implements AddToCartService {

    private final CustomerTradePhoneRepo tradePhoneRepo;
    private final SellingPhoneRepo sellingPhoneRepo;
    private final TradeAddCartRepo tradeAddCartRepo;
    private final SellingAddCartRepo sellingAddCartRepo;

    @Override
    public void addToCart(Long userId, Long phoneId, String tableName) {
        switch (tableName) {
            case "CUSTOMER_IPHONES" -> {
                // Check if already in cart (optional - add method to repo if needed)
                tradeAddCartRepo.save(TradePhoneCartUser.builder()
                        .userId(userId)
                        .sellingPhoneId(phoneId.intValue())
                        .build());

                CustomerTradePhone phone = tradePhoneRepo.findById(phoneId)
                        .orElseThrow(() -> new EntityNotFoundException("Trade phone not found"));

                phone.setCartCount(phone.getCartCount() + 1); // Optional if you maintain count
                tradePhoneRepo.save(phone);
            }

            case "SELLING_PHONES" -> {
                sellingAddCartRepo.save(SellingPhoneCartUser.builder()
                        .userId(userId)
                        .sellingPhoneId(phoneId.intValue())
                        .build());

                SellingPhone phone = sellingPhoneRepo.findById(phoneId.intValue())
                        .orElseThrow(() -> new EntityNotFoundException("Selling phone not found"));

                phone.setCartCount(phone.getCartCount() + 1); // Optional if you maintain count
                sellingPhoneRepo.save(phone);
            }

            default -> throw new IllegalArgumentException("Invalid table name: " + tableName);
        }
    }
}
