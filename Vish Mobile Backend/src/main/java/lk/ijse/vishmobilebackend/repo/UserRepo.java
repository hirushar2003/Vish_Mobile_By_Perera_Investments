package lk.ijse.vishmobilebackend.repo;

import lk.ijse.vishmobilebackend.entity.User;
import lk.ijse.vishmobilebackend.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAllByUserType(UserType userType);
}
