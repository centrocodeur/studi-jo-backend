package com.marien.studi_jo_backend.services.customer.review;

import com.marien.studi_jo_backend.dto.OrderedTicketsResponseDto;
import com.marien.studi_jo_backend.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {

   OrderedTicketsResponseDto getOrderedTicketsDetailsByOrderId(Long orderId);

    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
}
