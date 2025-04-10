package com.marien.studi_jo_backend.dto;


import lombok.Data;

@Data
public class CartItemsDto {

    private Long id;

    private Long price;

    private Long quantity;

    private Long ticketId;

    private Long orderId;

    private String ticketTitle;

    private byte [] returnedImg;

    private Long userId;


}
