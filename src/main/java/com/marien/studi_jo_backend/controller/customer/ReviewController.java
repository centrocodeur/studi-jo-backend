package com.marien.studi_jo_backend.controller.customer;


import com.marien.studi_jo_backend.dto.OrderedTicketsResponseDto;
import com.marien.studi_jo_backend.dto.ReviewDto;
import com.marien.studi_jo_backend.services.customer.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/ordered-tickets/{orderId}")
    public ResponseEntity<OrderedTicketsResponseDto> getOrderedTicketsDetailsById(@PathVariable Long orderId){

        return ResponseEntity.ok(reviewService.getOrderedTicketsDetailsByOrderId(orderId));

    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@ModelAttribute ReviewDto reviewDto) throws IOException {
        ReviewDto reviewDto1= reviewService.giveReview(reviewDto);
        if(reviewDto1==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewDto1);
    }
}
