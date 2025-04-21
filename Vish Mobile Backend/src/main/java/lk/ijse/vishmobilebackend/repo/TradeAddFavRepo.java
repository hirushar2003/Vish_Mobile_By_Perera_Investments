package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.TradePhoneFavUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeAddFavRepo extends JpaRepository<TradePhoneFavUser , Long> {
    boolean existsByUserIdAndTradePhoneId(Long userId, Long tradePhoneId);
    List<TradePhoneFavUser> findByUserId(Long userId);
}
