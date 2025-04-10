package com.marien.studi_jo_backend.controller.customer;


import com.marien.studi_jo_backend.dto.AddTicketInCartDto;
import com.marien.studi_jo_backend.dto.OrderDto;
import com.marien.studi_jo_backend.dto.PlaceOrderDto;
import com.marien.studi_jo_backend.exceptions.ValidationException;
import com.marien.studi_jo_backend.services.customer.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {



    private final CartService cartService;

    @PostMapping("/ticket_cart")
    public ResponseEntity<?> addTicketToCart(@RequestBody AddTicketInCartDto addTicketInCartDto){
        return cartService.addTicketToCart(addTicketInCartDto);
    }

    @GetMapping("/ticket_cart/{userId}")
    public ResponseEntity<?>getCartByUserId(@PathVariable Long userId){
        OrderDto orderDto = cartService.getCartByUSerId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/ticket/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code){
        try {
            OrderDto orderDto= cartService.applyCoupon(userId, code);
            return ResponseEntity.ok(orderDto);
        }catch (ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/ticket/addition")
    public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody AddTicketInCartDto addTicketInCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addTicketInCartDto));
    }


    @PostMapping("/ticket/deduction")
    public ResponseEntity<OrderDto> decreaseProductQuantity(@RequestBody AddTicketInCartDto addTicketInCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseProductQuantity(addTicketInCartDto));
    }


    @PostMapping("/ticket/placeOrder")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDto));
    }


    @GetMapping("/cart/myOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getMyPlacedOrders(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getMyPlacedOrders(userId));
    }
}
