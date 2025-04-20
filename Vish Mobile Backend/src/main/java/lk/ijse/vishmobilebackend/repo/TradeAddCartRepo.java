package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.TradePhoneCartUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeAddCartRepo extends JpaRepository<TradePhoneCartUser , Long> {
}
