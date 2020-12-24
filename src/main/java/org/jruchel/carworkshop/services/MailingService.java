package org.jruchel.carworkshop.services;

import org.jruchel.carworkshop.entities.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class MailingService {

    private String sender;
    @Autowired
    private JavaMailSender javaMailSender;

    public MailingService() {
        try {
            this.sender = org.jruchel.carworkshop.utils.Properties.getInstance().readProperty("spring.mail.username");
        } catch (IOException e) {
            this.sender = "jruchel254@gmail.com";
        }
    }

    private void sendEmail(String from, String to, String subject, String message, List<Byte[]> attachments) throws MessagingException {
        if (attachments == null || attachments.size() == 0) {
            sendEmail(from, to, subject, message);
        } else {
            System.out.println();
        }
    }

    public void sendEmail(String to, String subject, String content) {
        sendEmail(sender, to, subject, content);
    }

    public void sendEmail(Email email) throws MessagingException {
        sendEmail(sender, email.getTo(), email.getSubject(), email.getMessage(), email.getAttachments());
    }

    private void sendEmail(String from, String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }
}
