package com.marien.studi_jo_backend.controller.admin;

import com.marien.studi_jo_backend.dto.TicketCategoryDto;
import com.marien.studi_jo_backend.entity.TicketCategory;
import com.marien.studi_jo_backend.services.admin.category.TicketCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminTicketCategoryController {


    private final TicketCategoryService ticketCategoryService;

    @PostMapping("ticket_category")
    public ResponseEntity<TicketCategory> createCategory(@RequestBody TicketCategoryDto ticketCategoryDto){


        TicketCategory ticketCategory = ticketCategoryService.createTicketCategory(ticketCategoryDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCategory);

    }

    @GetMapping("allCategories")
    public ResponseEntity<List<TicketCategory>> getAllTicketCategories(){
        return ResponseEntity.ok(ticketCategoryService.getAllTicketCategories());
    }

}