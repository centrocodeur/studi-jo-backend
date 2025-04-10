package com.marien.studi_jo_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marien.studi_jo_backend.dto.TicketDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    private Long price;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TicketCategory ticketCategory;

    public TicketDto getDto(){
        TicketDto ticketDto= new TicketDto();

        ticketDto.setId(id);

        ticketDto.setPrice(price);
        ticketDto.setDescription(description);
        ticketDto.setByteImg(img);
        ticketDto.setCategoryId(ticketCategory.getId());
        ticketDto.setCategoryName(ticketCategory.getName());

        return ticketDto;
    }


}
