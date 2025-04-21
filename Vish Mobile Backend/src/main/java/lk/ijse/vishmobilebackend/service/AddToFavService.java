package lk.ijse.vishmobilebackend.service;

import lk.ijse.vishmobilebackend.dto.TradePhoneWithPhotosDTO;
import lk.ijse.vishmobilebackend.dto.UserFavPhonesDTO;

import java.util.List;

public interface AddToFavService {
    void addToFav(Long userId, Long phoneId, String tableName);
    List<TradePhoneWithPhotosDTO> getUserFavPhoneWithPhotos(Long userId);
    UserFavPhonesDTO getUserFavPhones(Long userId);
}
