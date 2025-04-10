package com.marien.studi_jo_backend.dto;

import lombok.Data;

@Data
public class PlaceOrderDto {
    private Long userId;

    private String email;

    private String orderDescription;
}