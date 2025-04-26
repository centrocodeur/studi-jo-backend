package com.marien.studi_jo_backend.services.customer.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.marien.studi_jo_backend.dto.QrCodeDto;
import com.marien.studi_jo_backend.entity.Order;
import com.marien.studi_jo_backend.entity.QrCode;
import com.marien.studi_jo_backend.repository.QrCodeRepository;
import com.marien.studi_jo_backend.services.validation.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;

@Service
public class QrCodeServiceImpl implements QrCodeService{

    @Autowired
    private  QrCodeRepository qrCodeRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void generateQrCode(Order order) throws WriterException, IOException, MessagingException {
        var qrCodeWriter = new QRCodeWriter();
        String fileName = "votrebilletelectronique.png";

        QrCode qrCode = new QrCode();



        BitMatrix bitMatrix = qrCodeWriter.encode("OrderTrackingId: "+ order.getTrackingId()+ "\n" +
                "CustomerTrackingId : "+ order.getUser().getUserTrackingId(),
                BarcodeFormat.QR_CODE, 200, 200);



        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        qrCode.setImg(pngOutputStream.toByteArray());

        qrCodeRepository.save(qrCode);


        BufferedImage image = ImageIO.read(new ByteArrayInputStream(qrCode.getImg()));
        ImageIO.write(image, "png", new File(fileName));


        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("centrocodeur@gmail.com");
        helper.setTo(order.getUser().getEmail());
        helper.setSubject("Votre billet des jeux Olympiques de Paris 2024");

        helper.setText(
                "<html><body> <p>Bonjour "+ order.getUser().getFirstname()+", </p> <br>" +
                        "<p> Veuillez trouvez ci-joint votre billet électronique pour les Jeux Olympiques de Paris 2024<p> <br>"+
                        "<h2> </h2> votre billet electronique</h2> </body> </html>", true);

        helper.addAttachment("votrebilletelectronique", new File("./votrebilletelectronique.png"));


        javaMailSender.send(message);


    }
}
