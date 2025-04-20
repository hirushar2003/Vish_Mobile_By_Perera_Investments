package lk.ijse.vishmobilebackend.controller;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.vishmobilebackend.dto.ResponseDTO;
import lk.ijse.vishmobilebackend.service.AddToCartService;
import lk.ijse.vishmobilebackend.service.AddToFavService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/addTo")
@RequiredArgsConstructor
public class CustomerPhoneBuyController {

    private final AddToFavService addToFavService;
    private final AddToCartService addToCartService;

    @PostMapping("/fav")
    public ResponseEntity<ResponseDTO> addToFavorite(
            @RequestParam Long userId,
            @RequestParam Long phoneId,
            @RequestParam String tableName) {
        try {
            addToFavService.addToFav(userId, phoneId, tableName.toUpperCase());
            return ResponseEntity.ok(new ResponseDTO(200, "Added to favorites successfully", null));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(404, ex.getMessage(), null));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(409, ex.getMessage(), null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "An unexpected error occurred while adding to favorites", null));
        }
    }

    @PostMapping("/cart")
    public ResponseEntity<ResponseDTO> addToCart(
            @RequestParam Long userId,
            @RequestParam Long phoneId,
            @RequestParam String tableName) {
        try {
            addToCartService.addToCart(userId, phoneId, tableName.toUpperCase());
            return ResponseEntity.ok(new ResponseDTO(200, "Added to cart successfully", null));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(404, ex.getMessage(), null));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(409, ex.getMessage(), null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "An unexpected error occurred while adding to cart", null));
        }
    }
}

