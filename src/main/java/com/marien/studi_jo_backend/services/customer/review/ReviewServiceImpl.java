package com.marien.studi_jo_backend.services.customer.review;

import com.marien.studi_jo_backend.dto.OrderedTicketsResponseDto;
import com.marien.studi_jo_backend.dto.ReviewDto;
import com.marien.studi_jo_backend.dto.TicketDto;
import com.marien.studi_jo_backend.entity.*;
import com.marien.studi_jo_backend.repository.OrderRepository;
import com.marien.studi_jo_backend.repository.ReviewRepository;
import com.marien.studi_jo_backend.repository.TicketRepository;
import com.marien.studi_jo_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

   private final OrderRepository orderRepository;

   private final ReviewRepository reviewRepository;

   private final TicketRepository ticketRepository;

   private final UserRepository userRepository;



    @Override
    public OrderedTicketsResponseDto getOrderedTicketsDetailsByOrderId(Long orderId) {
        Optional<Order> optionalOrder=orderRepository.findById(orderId);
        OrderedTicketsResponseDto orderedProductsResponseDto= new OrderedTicketsResponseDto();
        if(optionalOrder.isPresent()){
            orderedProductsResponseDto.setOrderAmount(optionalOrder.get().getAmount());
            List<TicketDto> ticketDtoList= new ArrayList<>();

            for(CartItems cartItems : optionalOrder.get().getCartItems()){

                TicketDto ticketDto= new TicketDto();

                ticketDto.setId(cartItems.getTicket().getId());
                ticketDto.setTitle(cartItems.getTicket().getTitle());
                ticketDto.setPrice(cartItems.getPrice());
                ticketDto.setQuantity(cartItems.getQuantity());

                ticketDto.setByteImg(cartItems.getTicket().getCompetition().getImg());

                ticketDtoList.add(ticketDto);
            }
            orderedProductsResponseDto.setTicketDtoList(ticketDtoList);
        }
        return null;
    }

    @Override
    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException {
        Optional<Ticket> optionalTicket = ticketRepository.findById(reviewDto.getTicketId());
        Optional<User> optionalUser = userRepository.findById(reviewDto.getUserId());

        if(optionalTicket.isPresent() && optionalUser.isPresent()){
            Review review = new Review();

            review.setRating(reviewDto.getRating());
            review.setDescription(reviewDto.getDescription());
            review.setUser(optionalUser.get());
            review.setTicket(optionalTicket.get());
            review.setImg(reviewDto.getImg().getBytes());

            return  reviewRepository.save(review).getDto();

        }
        return null;
    }
}
