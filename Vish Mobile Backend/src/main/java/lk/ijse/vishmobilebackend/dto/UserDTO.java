package lk.ijse.vishmobilebackend.dto;

import lk.ijse.vishmobilebackend.model.UserType;
import lk.ijse.vishmobilebackend.model.UserStatus;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private UserType userType;
    private String contactNumber;
    private String address;
    private UserStatus status;
}



