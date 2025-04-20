package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.TradePhoneFavUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeAddFavRepo extends JpaRepository<TradePhoneFavUser , Long> {
    boolean existsByUserIdAndTradePhoneId(Long userId, Long tradePhoneId);
}
