package com.marien.studi_jo_backend.services.validation;

import com.marien.studi_jo_backend.entity.Validation;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

@AllArgsConstructor
@Service
public class NotificationService {


    JavaMailSender javaMailSender;



    public void sendMessage(Validation validation) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("centrocodeur@gmail.com");
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Votre code d'activation");
        String texte = String.format(
                "Bonjour %s\n" + " Votre code d'activation est:  %s\n" + "Veuiller votre compte" +
                        "A bientôt",
                validation.getUser().getFirstname(),
                validation.getCode());
        message.setText(texte);

        javaMailSender.send(message);


    }


    public String qrCodeSending(String receiverEmailId, String userFirstName, String imageFile ){

        try {
            MimeMessage message = javaMailSender.createMimeMessage();


            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("centrocodeur@gmail.com");
            helper.setTo(receiverEmailId);
            helper.setSubject("Votre billet des jeux Olympiques de Paris 2024");

            helper.setText(
                    "<html><body> <p>Bonjour "+ userFirstName +", </p> <br>" +
                            "<p> Voici votre billet électronique <p> <br>"+
                            " <img src='"+ imageFile +"'> </body> </html>", true);

            FileSystemResource resource = new FileSystemResource(imageFile);
            helper.addInline("qr",new File(imageFile));
            helper.addAttachment(resource.getFilename(),resource);

            javaMailSender.send(message);
            System.out.println("Mail sent ok");
            return "Sending message ok";
        } catch (Exception e){
            return e.getMessage();
        }


    }


}