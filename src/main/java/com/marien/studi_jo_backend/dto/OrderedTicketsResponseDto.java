package com.marien.studi_jo_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderedTicketsResponseDto {

    private List<TicketDto> ticketDtoList;

    private Long orderAmount;
}
