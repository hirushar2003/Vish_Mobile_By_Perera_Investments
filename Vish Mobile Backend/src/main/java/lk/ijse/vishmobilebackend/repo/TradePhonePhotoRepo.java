package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.TradePhonePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradePhonePhotoRepo extends JpaRepository<TradePhonePhoto, Integer> {
    List<TradePhonePhoto> findByPhoneId(int phoneId);
}
