package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.SellingPhonePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellingPhonePhotoRepo extends JpaRepository<SellingPhonePhoto, Integer> {
    void deleteByPhoneId(int phoneId);
    List<SellingPhonePhoto> findByPhoneId(int phoneId);
    List<SellingPhonePhoto> findPhotoUrlsByPhoneId(int phoneId);
}
