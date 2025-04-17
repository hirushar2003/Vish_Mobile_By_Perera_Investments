package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.CustomerTradePhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTradePhoneRepo extends JpaRepository<CustomerTradePhone, Long> {
    List<CustomerTradePhone> findByUserId(Long userId);
}

