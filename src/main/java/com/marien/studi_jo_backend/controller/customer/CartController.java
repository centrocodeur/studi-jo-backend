package com.marien.studi_jo_backend.controller.customer;


import com.google.zxing.WriterException;
import com.marien.studi_jo_backend.dto.AddTicketInCartDto;
import com.marien.studi_jo_backend.dto.OrderDto;
import com.marien.studi_jo_backend.dto.PlaceOrderDto;
import com.marien.studi_jo_backend.exceptions.ValidationException;
import com.marien.studi_jo_backend.services.customer.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor

public class CartController {



    private final CartService cartService;

    @PostMapping("/ticket_cart")
    @Operation(summary = "Ajouter un ticket au panier")
    public ResponseEntity<?> addTicketToCart(@RequestBody AddTicketInCartDto addTicketInCartDto){
        return cartService.addTicketToCart(addTicketInCartDto);
    }


    @GetMapping("/ticket_cart/{userId}")
    @Operation(summary = "Rechercher un ticket à l'aide de son identifiant")
    public ResponseEntity<?>getCartByUserId(@PathVariable Long userId){
        OrderDto orderDto = cartService.getCartByUSerId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @DeleteMapping("/ticket_cart/{userId}")
    @Operation(summary = "Supprimer un ticket")
    public ResponseEntity<?> deleteTicketFromCartByUserId(@PathVariable Long userId){
        boolean deleted = cartService.removeCartByUserId(userId);

        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/ticket/coupon/{userId}/{code}")
    @Operation(summary = "Utiliser un coupon de réduction")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code){
        try {
            OrderDto orderDto= cartService.applyCoupon(userId, code);
            return ResponseEntity.ok(orderDto);
        }catch (ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }



    @PostMapping("/ticket/addition")
    @Operation(summary = "Augmenter le nombre de ticket")
    public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody AddTicketInCartDto addTicketInCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addTicketInCartDto));
    }


    @PostMapping("/ticket/deduction")
    @Operation(summary = "Diminuer le nombre de ticket")
    public ResponseEntity<OrderDto> decreaseProductQuantity(@RequestBody AddTicketInCartDto addTicketInCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseProductQuantity(addTicketInCartDto));
    }


    @PostMapping("/ticket/placeOrder")
    @Operation(summary = "Payement du ticket")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) throws IOException, WriterException, MessagingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDto));
    }


    @GetMapping("/myOrders/{userId}")
    @Operation(summary = "Liste de achats")
    public ResponseEntity<List<OrderDto>> getMyPlacedOrders(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getMyPlacedOrders(userId));
    }
}
