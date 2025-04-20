package lk.ijse.vishmobilebackend.service;

import lk.ijse.vishmobilebackend.dto.UserDTO;
import lk.ijse.vishmobilebackend.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User registerUser(UserDTO userDTO);
    List<User> getAllCustomers();
    User updateUserStatus(Integer id, String status);
    Optional<User> getUserByEmail(String email);

}
