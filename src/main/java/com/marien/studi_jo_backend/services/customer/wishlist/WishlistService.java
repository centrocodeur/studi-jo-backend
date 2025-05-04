package com.marien.studi_jo_backend.services.customer.wishlist;

import com.marien.studi_jo_backend.dto.WishlistDto;

import java.util.List;

public interface WishlistService {
    WishlistDto addTicketToWishlist(WishlistDto wishlistDto);

    List<WishlistDto> getWishlistByUserId(Long userId);
}
