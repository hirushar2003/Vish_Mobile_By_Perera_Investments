package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.PhoneBuyingPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBuyingPriceRepo extends JpaRepository<PhoneBuyingPrice, Integer> {
    @Query("SELECT AVG(p.price) FROM PhoneBuyingPrice p WHERE LOWER(TRIM(p.model)) = LOWER(TRIM(:model)) AND LOWER(TRIM(p.storage)) = LOWER(TRIM(:storage))")
    Double findPriceByModelAndStorage(@Param("model") String model, @Param("storage") String storage);


}

