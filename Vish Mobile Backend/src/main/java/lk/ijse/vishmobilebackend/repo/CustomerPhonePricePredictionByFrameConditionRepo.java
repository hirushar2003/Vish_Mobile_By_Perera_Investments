package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.CustomerPhonePricePredictionFrameNego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPhonePricePredictionByFrameConditionRepo extends JpaRepository<CustomerPhonePricePredictionFrameNego, Long>  {
    @Query("SELECT f.amountReduced FROM CustomerPhonePricePredictionFrameNego f WHERE f.model = :model AND f.frameCondition = :frameCondition")
    Double findFrameReductionAmount(@Param("model") String model, @Param("frameCondition") String frameCondition);
}
