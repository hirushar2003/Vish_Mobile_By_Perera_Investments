package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.CustomerTradePhone;
import lk.ijse.vishmobilebackend.model.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTradePhoneRepo extends JpaRepository<CustomerTradePhone, Long> {
    List<CustomerTradePhone> findByUserId(Long userId);

    @Query("SELECT c FROM CustomerTradePhone c WHERE c.approval = :status")
    List<CustomerTradePhone> findApprovedPhones(@Param("status") ApprovalStatus status);

    boolean existsById(Long id);
}

