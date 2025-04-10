package com.marien.studi_jo_backend.services.admin.ticket;

import com.marien.studi_jo_backend.dto.TicketDto;

import java.io.IOException;
import java.util.List;

public interface AdminTicketService {

    TicketDto addTicket(TicketDto ticketDto) throws IOException;

    List<TicketDto> getAllTicket();

    List<TicketDto>getAllProductByTitle(String title);


    boolean deleteTicket(Long id);


    TicketDto getTicketById(Long ticketId);

    TicketDto updateTicket(Long ticketId, TicketDto ticketDto) throws IOException;
}
