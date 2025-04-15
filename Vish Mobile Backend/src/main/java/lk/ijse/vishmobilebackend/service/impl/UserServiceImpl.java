package lk.ijse.vishmobilebackend.service.impl;

import lk.ijse.vishmobilebackend.dto.UserDTO;
import lk.ijse.vishmobilebackend.entity.User;
import lk.ijse.vishmobilebackend.model.UserStatus;
import lk.ijse.vishmobilebackend.model.UserType;
import lk.ijse.vishmobilebackend.repo.UserRepo;
import lk.ijse.vishmobilebackend.repo.UserRepoCustom;
import lk.ijse.vishmobilebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepoCustom userRepositoryCustom;

    public UserServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUserType(userDTO.getUserType() != null ? userDTO.getUserType() : UserType.customer);
        user.setContactNumber(userDTO.getContactNumber());
        user.setAddress(userDTO.getAddress());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Change this method to search by email, not by username
        Optional<User> user = userRepository.findByEmail(email);  // Modify to email query
        return user.map(u -> new org.springframework.security.core.userdetails.User(
                u.getEmail(), u.getPassword(), new java.util.ArrayList<>()
        )).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Override
    public User loginUser(String email, String password) {
        User user = userRepositoryCustom.findUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        // Debugging log to check passwords
        System.out.println("Provided password: " + password);
        System.out.println("Stored password (hashed): " + user.getPassword());
        System.out.println("Stored email: " + user.getEmail());

        // Use BCrypt to match the password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        return user;
    }

    @Override
    public List<User> getAllCustomers() {
        return userRepository.findAllByUserType(UserType.customer);
    }

    @Override
    public User updateUserStatus(Integer id, String status) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserStatus userStatus = UserStatus.valueOf(status.toUpperCase());
            user.setStatus(userStatus);
            userRepository.save(user);
            return user;  // Return the full User object with updated status
        }
        throw new RuntimeException("User not found");
    }


}
