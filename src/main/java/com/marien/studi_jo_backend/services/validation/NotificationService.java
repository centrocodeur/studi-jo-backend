package com.marien.studi_jo_backend.services.validation;

import com.marien.studi_jo_backend.entity.Validation;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

}