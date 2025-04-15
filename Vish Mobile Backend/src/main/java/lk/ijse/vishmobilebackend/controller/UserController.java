package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.entity.User;
import lk.ijse.vishmobilebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
