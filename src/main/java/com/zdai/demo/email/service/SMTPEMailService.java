package com.zdai.demo.email.service;

public interface SMTPEMailService {
    public void sendMail(String htmlBody, String subject);
}
