package com.zdai.demo.email.service;

import javax.mail.internet.MimeMessage;

public interface MessageService {
    public String extractEmailHtmlBody(MimeMessage msg);
    public boolean isTextIsHtml();
}
