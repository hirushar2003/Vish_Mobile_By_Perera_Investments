package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.SellingPhonePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellingPhonePhotoRepo extends JpaRepository<SellingPhonePhoto, Integer> {
    void deleteByPhoneId(int phoneId);
    List<SellingPhonePhoto> findByPhoneId(int phoneId);
}
