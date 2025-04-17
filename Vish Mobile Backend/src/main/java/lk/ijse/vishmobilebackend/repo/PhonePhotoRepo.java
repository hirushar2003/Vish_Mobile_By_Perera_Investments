package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.PhonePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhonePhotoRepo extends JpaRepository<PhonePhoto, Integer> {
    void deleteByPhoneId(int phoneId);
    List<PhonePhoto> findByPhoneId(int phoneId);
}
