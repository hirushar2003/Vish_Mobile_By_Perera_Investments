package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.CustomerPhonePricePrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPhonePricePredictionRepo extends JpaRepository<CustomerPhonePricePrediction, Integer> {


}
