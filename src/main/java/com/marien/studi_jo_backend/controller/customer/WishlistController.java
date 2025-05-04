package com.marien.studi_jo_backend.controller.customer;


import com.marien.studi_jo_backend.dto.WishlistDto;
import com.marien.studi_jo_backend.services.customer.wishlist.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class WishlistController {


    private final WishlistService wishlistService;



    @PostMapping("/wishlist")
    public ResponseEntity<?> addProductToWishlist(@RequestBody WishlistDto wishlistDto){
        WishlistDto postedWishlistDto= wishlistService.addTicketToWishlist(wishlistDto);

        if(postedWishlistDto==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(postedWishlistDto);
    }

    @GetMapping("/wishlist/{userId}")
    public ResponseEntity<List<WishlistDto>> getWishlistByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(wishlistService.getWishlistByUserId(userId));
    }
}
