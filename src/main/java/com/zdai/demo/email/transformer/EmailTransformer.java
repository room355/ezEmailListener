package com.zdai.demo.email.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailTransformer extends AbstractMailMessageTransformer<MimeMessage> {

    private final Logger logger = LoggerFactory.getLogger(EmailTransformer.class);

    @Override
    protected AbstractIntegrationMessageBuilder<MimeMessage> doTransform(Message message) {
        MimeMessage mimeMsg = null;
        try {
            logger.info("============== Transforming Email: {} ==============", message.getSubject());
            mimeMsg = (MimeMessage) message;
        } catch (MessagingException e) {
            logger.error("Transforming with Error: {}", e.getCause());
        }
        return MessageBuilder.withPayload(mimeMsg);
    }
}
