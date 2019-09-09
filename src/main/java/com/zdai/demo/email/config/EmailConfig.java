package com.zdai.demo.email.config;

import com.zdai.demo.email.pojo.EmailSenderProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class EmailConfig {
    @Value("${my.email.username}")
    private String username;

    @Value("${my.email.password}")
    private String password;

    @Value("${my.email.host}")
    private String host;

    @Value("${my.email.mailbox}")
    private String mailbox;

    @Value("${my.email.poll-rate}")
    private String pollRate;

    @Bean
    public EmailSenderProperties emailProperties() {
        return new EmailSenderProperties(username, password, host, mailbox, pollRate);
    }
}
