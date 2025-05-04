package com.marien.studi_jo_backend.services.customer.cart;

import com.google.zxing.WriterException;
import com.marien.studi_jo_backend.dto.AddTicketInCartDto;
import com.marien.studi_jo_backend.dto.OrderDto;
import com.marien.studi_jo_backend.dto.PlaceOrderDto;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CartService {


    ResponseEntity<?> addTicketToCart(AddTicketInCartDto addTicketInCartDto);

    OrderDto getCartByUSerId(Long userId);

    OrderDto  applyCoupon(Long userId, String code);

    OrderDto increaseProductQuantity(AddTicketInCartDto addTicketInCartDto);

    OrderDto decreaseProductQuantity(AddTicketInCartDto addTicketInCartDto);
    OrderDto  placeOrder(PlaceOrderDto placeOrderDto) throws IOException, WriterException, MessagingException;

    List<OrderDto> getMyPlacedOrders(Long userId);

    OrderDto  searchOrderByTrackingId(UUID trackingId);

    //boolean removeCartById(Long cartId);
   boolean removeCartByUserId(Long userId);
}
