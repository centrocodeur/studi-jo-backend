package com.marien.studi_jo_backend.controller.customer;


import com.marien.studi_jo_backend.dto.TicketDetailDto;
import com.marien.studi_jo_backend.dto.TicketDto;
import com.marien.studi_jo_backend.services.customer.ticket.CustomerTicketService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Liste de tous les tickets")
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        List<TicketDto> ticketDtos = customerTicketService.getAllTicket();

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/ticket/search/{name}")
    @Operation(summary = "Recherche d'un ticket par un mot clé")
    public ResponseEntity<List<TicketDto>> getAllTicketByName(@PathVariable String name) {
        List<TicketDto> ticketDtos = customerTicketService.searchTicketByTitle(name);

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/ticket_by_description/search/{description}")
    @Operation(summary = "Recherche d'un ticket par un mot clé")
    public ResponseEntity<List<TicketDto>> getAllTicketByDescription(@PathVariable String description) {
        List<TicketDto> ticketDtos = customerTicketService.searchTicketByDescription(description);

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/ticket/{ticketId}")
    @Operation(summary = "Obtenir les details de ticket")
    public ResponseEntity<TicketDetailDto> getTicketDetailById(@PathVariable Long ticketId) {
        TicketDetailDto ticketDetailDto = customerTicketService.getTicketDetailById(ticketId);
        if (ticketDetailDto == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(ticketDetailDto);

    }
}
