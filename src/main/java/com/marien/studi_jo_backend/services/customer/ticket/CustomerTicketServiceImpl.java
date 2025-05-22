package com.marien.studi_jo_backend.services.customer.ticket;

import com.marien.studi_jo_backend.dto.TicketDetailDto;
import com.marien.studi_jo_backend.dto.TicketDto;
import com.marien.studi_jo_backend.entity.Review;
import com.marien.studi_jo_backend.entity.Ticket;
import com.marien.studi_jo_backend.repository.ReviewRepository;
import com.marien.studi_jo_backend.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerTicketServiceImpl implements CustomerTicketService {

    private final TicketRepository ticketRepository;

   // private  final FAQRepository faqRepository;

    private final ReviewRepository reviewRepository;




    public List<TicketDto> getAllTicket(){
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(Ticket::getDto).collect(Collectors.toList());
    }

    public List<TicketDto>searchTicketByTitle(String title){
        List<Ticket> tickets = ticketRepository.findAllByTitleContaining(title);
        return tickets.stream().map(Ticket::getDto).collect(Collectors.toList());
    }

    public List<TicketDto>searchTicketByDescription(String description){
        List<Ticket> tickets = ticketRepository.findAllByDescriptionContaining(description);
        return tickets.stream().map(Ticket::getDto).collect(Collectors.toList());
    }



    @Override
    public TicketDetailDto getTicketDetailById(Long ticketId) {
        Optional<Ticket> optionalTicket= ticketRepository.findById(ticketId);


        if(optionalTicket.isPresent()){
            List<Review> reviewList = reviewRepository.findAllByTicketId(ticketId);

            TicketDetailDto ticketDetailDto = new TicketDetailDto();
            ticketDetailDto.setTicketDto(optionalTicket.get().getDto());
            ticketDetailDto.setReviewDtoList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));
            return ticketDetailDto;
        }
        return null;
    }

}
