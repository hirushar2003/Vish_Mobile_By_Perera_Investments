package lk.ijse.vishmobilebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "selling_phone_photos")
public class SellingPhonePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phone_id")
    private int phoneId;

    @Column(name = "photo_url")
    private String photoUrl;

    // Getters and Setters
}

