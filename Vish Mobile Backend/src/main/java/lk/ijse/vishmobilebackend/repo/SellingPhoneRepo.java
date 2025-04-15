package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.SellingPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellingPhoneRepo extends JpaRepository<SellingPhone, Integer> {
    @Query(value = "SELECT id FROM selling_phones ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Long findLastInsertedId();
}
