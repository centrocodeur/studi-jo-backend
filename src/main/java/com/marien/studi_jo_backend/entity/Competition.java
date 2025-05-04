package com.marien.studi_jo_backend.entity;

import com.marien.studi_jo_backend.dto.CompetitionDto;
import com.marien.studi_jo_backend.dto.TicketDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "competitions")
@Data
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String compDate;

    private String compTime;

    private String site;

    private String city;


    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;


    public CompetitionDto getDto(){

        CompetitionDto competitionDto = new CompetitionDto();

        competitionDto.setId(id);
        competitionDto.setName(name);
        competitionDto.setCompDate(compDate);
        competitionDto.setCompTime(compTime);
        competitionDto.setSite(site);
        competitionDto.setCity(city);
        competitionDto.setByteImg(img);

        return competitionDto;
    }
}
