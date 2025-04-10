package com.marien.studi_jo_backend.controller.customer;


import com.marien.studi_jo_backend.dto.TicketDetailDto;
import com.marien.studi_jo_backend.dto.TicketDto;
import com.marien.studi_jo_backend.services.customer.ticket.CustomerTicketService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerTicketController {


    private final CustomerTicketService customerTicketService;

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        List<TicketDto> ticketDtos = customerTicketService.getAllTicket();

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/ticket/search/{name}")
    public ResponseEntity<List<TicketDto>> getAllTicketByName(@PathVariable String name) {
        List<TicketDto> ticketDtos = customerTicketService.searchTicketByTitle(name);

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketDetailDto> getProductDetailById(@PathVariable Long ticketId) {
        TicketDetailDto ticketDetailDto = customerTicketService.getTicketDetailById(ticketId);
        if (ticketDetailDto == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(ticketDetailDto);

    }
}
