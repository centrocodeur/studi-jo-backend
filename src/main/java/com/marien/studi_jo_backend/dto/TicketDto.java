package com.marien.studi_jo_backend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TicketDto {

    private Long id;

    private String title;

    private String description;

    private Long price;

    private byte[] byteImg;

    private Long categoryId;

    private String categoryName;

    private MultipartFile img;

    private Long quantity;


}