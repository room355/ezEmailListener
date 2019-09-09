package com.zdai.demo.email.service;

import com.zdai.demo.email.handler.EmailHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class SMTPEMailServiceImpl implements SMTPEMailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailHandler.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.mailTo}")
    private String mailTo;

    @Value("${spring.mail.mailFrom}")
    private String mailFrom;

    @Override
    public void sendMail(String htmlBody, String subject) {
        MimeMessageHelper helper;
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            helper =new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setTo(this.mailTo);
            helper.setFrom(this.mailFrom);
            helper.setSubject(subject);
            helper.setText(htmlBody);
            logger.info("============== Sending Email: {} ==============", message.getSubject());
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
