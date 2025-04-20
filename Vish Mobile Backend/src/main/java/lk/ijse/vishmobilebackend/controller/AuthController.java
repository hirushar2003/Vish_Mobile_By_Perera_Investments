package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.util.JwtUtil;
import lk.ijse.vishmobilebackend.dto.AuthDTO;
import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.dto.UserDTO;
import lk.ijse.vishmobilebackend.entity.User;
import lk.ijse.vishmobilebackend.service.UserService;
import lk.ijse.vishmobilebackend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        User savedUser = userService.registerUser(userDTO);
        UserDetails userDetails = userService.loadUserByUsername(savedUser.getEmail());
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "User not found after registration", null));
        }
        String token = jwtUtil.generateToken(userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("email", savedUser.getEmail());
        response.put("username", savedUser.getUsername());
        response.put("userId", savedUser.getId());
        response.put("contact", savedUser.getContactNumber());
        response.put("address", savedUser.getAddress());
        response.put("token", token);
        response.put("message", "User registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword())
            );
            Optional<User> optionalUser = userService.getUserByEmail(authDTO.getEmail());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User not found", null));
            }

            User user = optionalUser.get();

            UserDetails userDetails = userService.loadUserByUsername(authDTO.getEmail());
            String token = jwtUtil.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getId());
            response.put("email", user.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid email or password", null));
        }
    }

}


