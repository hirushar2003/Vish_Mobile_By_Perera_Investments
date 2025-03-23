package lk.ijse.vishmobilebackend.controller;

import lk.ijse.vishmobilebackend.util.JwtUtil;
import lk.ijse.vishmobilebackend.dto.AuthDTO;
import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.dto.UserDTO;
import lk.ijse.vishmobilebackend.entity.User;
import lk.ijse.vishmobilebackend.service.UserService;
import lk.ijse.vishmobilebackend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        // Register user
        User savedUser = userService.registerUser(userDTO);

        // Ensure the user is properly loaded from DB
        UserDetails userDetails = userService.loadUserByUsername(savedUser.getEmail());  // Ensure it's by email

        if (userDetails == null) { // Ensure user is found
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "User not found after registration", null));
        }

        // Generate JWT Token
        String token = jwtUtil.generateToken(userDetails);

        // Prepare response
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
                    new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword())  // Use email
            );

            UserDetails userDetails = userService.loadUserByUsername(authDTO.getEmail());  // Use email
            String token = jwtUtil.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("email", authDTO.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid email or password", null));
        }
    }

    @PostMapping("/login/v2")
    public ResponseEntity<?> loginV2(@RequestBody AuthDTO authDTO) {
        try {
            // Using the custom login logic
            User user = userService.loginUser(authDTO.getEmail(), authDTO.getPassword());  // Use email

            // Generate JWT Token after custom login
            UserDetails userDetails = userService.loadUserByUsername(authDTO.getEmail());  // Use email
            String token = jwtUtil.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("email", authDTO.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, e.getMessage(), null));
        }
    }
}


