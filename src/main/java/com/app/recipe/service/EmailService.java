package com.app.recipe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

@Service
public class EmailService {
    private final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private JavaMailSender javaMailSender;
    private JavaMailSender mailSender;
    private JsonService jsonService;

    public EmailService(JavaMailSender javaMailSender, JavaMailSender mailSender, JsonService jsonService) {
        this.javaMailSender = javaMailSender;
        this.mailSender = mailSender;
        this.jsonService = jsonService;
    }

    public void sendEmail(Map<String, Double> mailContent)
            throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        LOGGER.info("Sending email");
        try {

            jsonService.exportToJson(mailContent);

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("****@gmail.com");
            helper.setTo("*****@gmail.com");
            helper.setSubject("AZ Recipe service: List of ingredients");
            helper.setText(String.valueOf(mailContent));

            FileSystemResource file = new FileSystemResource("Ingredients.json");
            helper.addAttachment(file.getFilename(), file);
            LOGGER.info("Email was sent");

        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        mailSender.send(message);
    }
}
