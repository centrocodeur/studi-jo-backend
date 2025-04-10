package com.marien.studi_jo_backend.entity;


import com.marien.studi_jo_backend.dto.ReviewDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rating;

    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;


    @ManyToOne(fetch= FetchType.LAZY, optional = false)
    @JoinColumn(name= "user_id", nullable= false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch= FetchType.LAZY, optional = false)
    @JoinColumn(name= "ticket_id", nullable= false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Ticket ticket;

    public ReviewDto getDto(){

        ReviewDto reviewDto= new ReviewDto();

        reviewDto.setId(id);
        reviewDto.setRating(rating);
        reviewDto.setDescription(description);
        reviewDto.setReturnedImg(img);
        reviewDto.setTicketId(ticket.getId());
        reviewDto.setUserId(user.getId());
        reviewDto.setUsername(user.getLastname());

        return reviewDto;
    }
}
