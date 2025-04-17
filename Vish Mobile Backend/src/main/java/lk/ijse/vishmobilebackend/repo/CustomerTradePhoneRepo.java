package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.CustomerTradePhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTradePhoneRepo extends JpaRepository<CustomerTradePhone, Long> {


}
