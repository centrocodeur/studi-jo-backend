package com.marien.studi_jo_backend.services.customer.qrcode;

import com.google.zxing.WriterException;
import com.marien.studi_jo_backend.dto.OrderDto;
import com.marien.studi_jo_backend.dto.PlaceOrderDto;
import com.marien.studi_jo_backend.dto.UserDto;
import com.marien.studi_jo_backend.entity.Order;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface QrCodeService {

    void generateQrCode(Order order) throws WriterException, IOException, MessagingException;



}
