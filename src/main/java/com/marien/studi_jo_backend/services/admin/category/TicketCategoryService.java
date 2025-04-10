package com.marien.studi_jo_backend.services.admin.category;

import com.marien.studi_jo_backend.dto.TicketCategoryDto;
import com.marien.studi_jo_backend.entity.TicketCategory;

import java.util.List;

public interface TicketCategoryService {

    TicketCategory createTicketCategory (TicketCategoryDto ticketCategoryDto);

    List<TicketCategory> getAllTicketCategories();

}
