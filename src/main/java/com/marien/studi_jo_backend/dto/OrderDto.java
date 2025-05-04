package com.marien.studi_jo_backend.dto;

import com.marien.studi_jo_backend.enums.OrderStatus;
import com.marien.studi_jo_backend.enums.PaymentType;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {

    private Long id;

    private String ordersDescription;

    private Date date;

    private Long amount;

    private String email;

    private PaymentType payment;

    private OrderStatus orderStatus;

    private Long totalAmount;

    private Long discount;

    private UUID trackingId;

    private String userName;

    private List<CartItemsDto> cartItems;

    private String couponName;


}
