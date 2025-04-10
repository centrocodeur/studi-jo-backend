package com.marien.studi_jo_backend.services.customer.ticket;

import com.marien.studi_jo_backend.dto.TicketDetailDto;
import com.marien.studi_jo_backend.dto.TicketDto;

import java.util.List;

public interface CustomerTicketService {

    List<TicketDto> getAllTicket();

    List<TicketDto>searchTicketByTitle(String title);



    TicketDetailDto getTicketDetailById(Long ticketId);
}