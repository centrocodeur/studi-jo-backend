package com.marien.studi_jo_backend.dto;


import lombok.Data;

@Data
public class WishlistDto {

    private  Long userId;

    private Long ticketId;

    private Long id;

    private String ticketTitle;

    private String ticketDescription;

    private  byte[] returnedImg;

    private Long price;

}
