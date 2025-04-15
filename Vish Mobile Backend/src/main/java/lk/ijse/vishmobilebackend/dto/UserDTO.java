package lk.ijse.vishmobilebackend.dto;

import lk.ijse.vishmobilebackend.model.UserType;
import lk.ijse.vishmobilebackend.model.UserStatus;  // Import the UserStatus enum
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private UserType userType;
    private String contactNumber;
    private String address;
    private UserStatus status;

    public UserDTO(String username, String email, String password, UserType userType, String contactNumber, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.contactNumber = contactNumber;
        this.address = address;
    }
}



