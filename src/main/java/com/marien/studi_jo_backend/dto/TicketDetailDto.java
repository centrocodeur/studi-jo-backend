package com.marien.studi_jo_backend.dto;


import lombok.Data;

import java.util.List;

@Data
public class TicketDetailDto {

    private TicketDto ticketDto;

    private List<ReviewDto> reviewDtoList;

   // private List<FAQDto> faqDtoList;
}
