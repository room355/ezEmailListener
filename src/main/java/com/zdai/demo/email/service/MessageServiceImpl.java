package com.zdai.demo.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class MessageServiceImpl implements MessageService {

    private final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private boolean textIsHtml;

    @Override
    public String extractEmailHtmlBody(MimeMessage msg) {
        StringBuilder htmlBuilder = new StringBuilder();
        try {
            Multipart multipart = (Multipart) msg.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                Part part = multipart.getBodyPart(i);
                htmlBuilder.append(getText(part));
            }
        } catch (IOException | MessagingException e) {
            logger.error("Extracting html body with Error: {}", e.getCause());
        }
        return htmlBuilder.toString();
    }

    private String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }
        if (p.isMimeType("multipart/alternative")) {
            Multipart mp = (Multipart) p.getContent();
            String text = "";
            for (int i = 0; i < mp.getCount(); i++) {
                Part part = mp.getBodyPart(i);
                if (part.isMimeType("text/plain")) {
                    if (text.equals("")) {
                        text = getText(part);
                    }
                    continue;
                } else if (part.isMimeType("text/html")) {
                    String s = getText(part);
                    if (s.length() != 0) {
                        return s;
                    } else {
                        return getText(part);
                    }
                }
                return text;
            }
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s.length() != 0) {
                    return s;
                }
            }
        }
        return "";
    }


    @Override
    public boolean isTextIsHtml() {
        return this.textIsHtml;
    }
}
