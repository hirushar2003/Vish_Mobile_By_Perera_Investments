package lk.ijse.vishmobilebackend.service;

import lk.ijse.vishmobilebackend.dto.UserDTO;
import lk.ijse.vishmobilebackend.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User registerUser(UserDTO userDTO);
    User loginUser(String email, String password);  // Change to use email
    List<User> getAllCustomers();
    User updateUserStatus(Integer id, String status);
}
