package lk.ijse.vishmobilebackend.dto;

import lk.ijse.vishmobilebackend.model.UserType;
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
}


