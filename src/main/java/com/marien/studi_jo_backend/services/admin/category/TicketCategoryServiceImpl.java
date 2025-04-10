package com.marien.studi_jo_backend.services.admin.category;

import com.marien.studi_jo_backend.dto.TicketCategoryDto;
import com.marien.studi_jo_backend.entity.TicketCategory;
import com.marien.studi_jo_backend.repository.TicketCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketCategoryServiceImpl  implements TicketCategoryService{


    private final TicketCategoryRepository ticketCategoryRepository;

    public TicketCategory createTicketCategory (TicketCategoryDto ticketCategoryDto){

        TicketCategory ticketCategory = new TicketCategory();

        ticketCategory.setName(ticketCategoryDto.getName());
        ticketCategory.setDescription(ticketCategoryDto.getDescription());

        return ticketCategoryRepository.save(ticketCategory);
    }

    public List<TicketCategory> getAllTicketCategories(){
        return ticketCategoryRepository.findAll();
    }
}


