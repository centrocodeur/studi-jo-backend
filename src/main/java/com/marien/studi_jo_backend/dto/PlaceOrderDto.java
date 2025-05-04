package com.marien.studi_jo_backend.dto;

import com.marien.studi_jo_backend.enums.PaymentType;
import lombok.Data;

@Data
public class PlaceOrderDto {
    private Long userId;

    private String payment;

    private String orderDescription;
}