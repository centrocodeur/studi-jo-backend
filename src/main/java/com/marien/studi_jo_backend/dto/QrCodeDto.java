package com.marien.studi_jo_backend.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class QrCodeDto {

    private Long id;

    private byte[] byteImg;

    private MultipartFile img;


}
