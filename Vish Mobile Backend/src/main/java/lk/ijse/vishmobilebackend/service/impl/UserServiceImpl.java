package lk.ijse.vishmobilebackend.service.impl;

import lk.ijse.vishmobilebackend.dto.UserDTO;
import lk.ijse.vishmobilebackend.entity.User;
import lk.ijse.vishmobilebackend.model.UserStatus;
import lk.ijse.vishmobilebackend.model.UserType;
import lk.ijse.vishmobilebackend.repo.UserRepo;
import lk.ijse.vishmobilebackend.service.UserService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUserType(userDTO.getUserType() != null ? userDTO.getUserType() : UserType.customer);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(u -> new org.springframework.security.core.userdetails.User(
                u.getEmail(), u.getPassword(), new java.util.ArrayList<>()
        )).orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
            user.setStatus(UserStatus.valueOf(status.toUpperCase()));
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            return userById;
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
