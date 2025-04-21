package lk.ijse.vishmobilebackend.service;

import lk.ijse.vishmobilebackend.dto.UserCartPhonesDTO;

public interface AddToCartService {
    void addToCart(Long userId, Long phoneId, String tableName);
    UserCartPhonesDTO getUserCartPhones(Long userId);
}

