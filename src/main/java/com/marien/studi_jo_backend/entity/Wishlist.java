package com.marien.studi_jo_backend.entity;


import com.marien.studi_jo_backend.dto.WishlistDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
public class Wishlist {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ticket ticket;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public WishlistDto getWishlistDto() {
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setId(id);
        wishlistDto.setTicketId(ticket.getId());
        wishlistDto.setReturnedImg(ticket.getCompetition().getImg());
        wishlistDto.setTicketTitle(ticket.getTitle());
        wishlistDto.setTicketDescription(ticket.getDescription());
        wishlistDto.setPrice(ticket.getPrice());
        wishlistDto.setUserId(user.getId());

        return wishlistDto;

    }
}
