package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.SellingPhoneFavUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellingAddFavRepo extends JpaRepository<SellingPhoneFavUser , Long> {
    boolean existsByUserIdAndSellingPhoneId(Long userId, Integer sellingPhoneId);
}
