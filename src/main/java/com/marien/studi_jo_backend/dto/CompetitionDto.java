package com.marien.studi_jo_backend.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;




@Data
public class CompetitionDto {

    private Long id;

    private String name;

    private String compDate;

    private String compTime;

    private String site;

    private byte[] byteImg;

    private MultipartFile img;


}
