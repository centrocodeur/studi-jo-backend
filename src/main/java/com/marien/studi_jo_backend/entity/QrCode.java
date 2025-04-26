package com.marien.studi_jo_backend.entity;


import com.marien.studi_jo_backend.dto.QrCodeDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "qc_codes")
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    public QrCodeDto getDto(){
        QrCodeDto qrCodeDto = new QrCodeDto();

        qrCodeDto.setByteImg(img);

        return qrCodeDto;
    }

}
