package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.SellingPhoneCartUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellingAddCartRepo extends JpaRepository<SellingPhoneCartUser, Long> {
    List<SellingPhoneCartUser> findByUserId(Long userId);
}

