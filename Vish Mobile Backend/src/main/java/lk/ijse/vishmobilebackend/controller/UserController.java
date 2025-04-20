package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.entity.User;
import lk.ijse.vishmobilebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/userManage")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllCustomers")
    public List<User> getAllCustomers() {
        return userService.getAllCustomers();
    }
    @PutMapping("/changeStatus/{id}")
    public User changeUserStatus(@PathVariable Integer id, @RequestBody User updatedUser) {
        String status = String.valueOf(updatedUser.getStatus());
        return userService.updateUserStatus(id, status.toUpperCase());
    }
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable Integer id) {
        try {
            Optional<User> userById = userService.getUserById(id);
            if (userById.isPresent()) {
                return ResponseEntity.ok(
                        new ResponseDTO(200, "User retrieved successfully", userById.get())
                );
            } else {
                return ResponseEntity.status(404).body(
                        new ResponseDTO(404, "User not found", null)
                );
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ResponseDTO(500, "An error occurred: " + e.getMessage(), null)
            );
        }
    }
}
