package com.marien.studi_jo_backend.services.admin.ticket;

import com.marien.studi_jo_backend.dto.TicketDto;
import com.marien.studi_jo_backend.entity.Competition;
import com.marien.studi_jo_backend.entity.Ticket;
import com.marien.studi_jo_backend.entity.TicketCategory;
import com.marien.studi_jo_backend.repository.CompetitionRepository;
import com.marien.studi_jo_backend.repository.TicketCategoryRepository;
import com.marien.studi_jo_backend.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminTicketServiceImpl implements AdminTicketService{

    private final TicketRepository ticketRepository;

    private  final TicketCategoryRepository ticketCategoryRepository;

    private final CompetitionRepository competitionRepository;


    public TicketDto addTicket(TicketDto ticketDto) throws IOException {

        Ticket ticket = new Ticket();

        ticket.setDescription(ticketDto.getDescription());
        ticket.setTitle(ticketDto.getTitle());
        ticket.setPrice(ticketDto.getPrice());
        //ticket.setImg(ticketDto.getImg().getBytes());

        TicketCategory category = ticketCategoryRepository.findById(ticketDto.getCategoryId()).orElseThrow();
        Competition competition = competitionRepository.findById(ticketDto.getCompetitionId()).orElseThrow();

        ticket.setTicketCategory(category);
        ticket.setCompetition(competition);

        return ticketRepository.save(ticket).getDto();



    }

    public List<TicketDto> getAllTicket(){
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(Ticket::getDto).collect(Collectors.toList());
    }


    public List<TicketDto>getAllProductByTitle(String title){
        List<Ticket> tickets = ticketRepository.findAllByTitleContaining(title);
        return tickets.stream().map(Ticket::getDto).collect(Collectors.toList());
    }

    public boolean deleteTicket(Long id){
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);

        if(optionalTicket.isPresent()){
            ticketRepository.deleteById(id);
            return true;
        }
        return false;

    }

    public TicketDto getTicketById(Long ticketId){
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);

        if(optionalTicket.isPresent()){
            return optionalTicket.get().getDto();
        }else {
            return null;
        }
    }



    public TicketDto updateTicket(Long ticketId, TicketDto ticketDto) throws IOException {
        Optional<Ticket> optionalTicket=ticketRepository.findById(ticketId);
        Optional<TicketCategory> optionalCategory = ticketCategoryRepository.findById(ticketDto.getCategoryId());
        Optional<Competition> optionalCompetition = competitionRepository.findById(ticketDto.getCompetitionId());

        if(optionalTicket.isPresent() && optionalCategory.isPresent() && optionalCompetition.isPresent()){
            Ticket ticket = optionalTicket.get();
            ticket.setTitle(ticketDto.getTitle());
            ticket.setPrice(ticketDto.getPrice());
            ticket.setDescription(ticketDto.getDescription());
            ticket.setTicketCategory(optionalCategory.get());
            ticket.setCompetition(optionalCompetition.get());

           /* if(ticketDto.getImg()!=null){
                ticket.setImg(ticketDto.getImg().getBytes());
            }*/

            return ticketRepository.save(ticket).getDto();

        }else {
            return null;
        }
    }

}
