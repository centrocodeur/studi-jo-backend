package com.marien.studi_jo_backend.repository;

import com.marien.studi_jo_backend.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemsRepository extends JpaRepository <CartItems, Long> {

    Optional<CartItems> findByTicketIdAndOrderIdAndUserId(Long ticketId, Long orderId, Long userId);
    Optional<CartItems> findByUserId(Long userId);
}
