package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.CustomerPhonePricePredictionBatteryNego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPhonePricePredictionBatteryNegoRepo extends JpaRepository<CustomerPhonePricePredictionBatteryNego, Long> {
    @Query("SELECT b.amountReduced FROM CustomerPhonePricePredictionBatteryNego b WHERE b.model = :model AND b.batteryHealthRange = :range")
    Double findBatteryReductionAmount(@Param("model") String model, @Param("range") String range);
}