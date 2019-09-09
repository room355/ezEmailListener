package com.zdai.demo.email.handler;

import com.zdai.demo.email.service.MessageService;
import com.zdai.demo.email.service.SMTPEMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailHandler implements GenericHandler<MimeMessage> {

    private static final Logger logger = LoggerFactory.getLogger(EmailHandler.class);

    @Autowired
    SMTPEMailService smtpEMailService;

    @Autowired
    MessageService messageService;

    @Override
    public Object handle(MimeMessage mimeMessage, MessageHeaders messageHeaders) {
        try {
            logger.info("============== Handling Mail: {} ==============", mimeMessage.getSubject());
            String htmlBody = messageService.extractEmailHtmlBody(mimeMessage);
            String subject = mimeMessage.getSubject();
            smtpEMailService.sendMail(htmlBody, subject);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
