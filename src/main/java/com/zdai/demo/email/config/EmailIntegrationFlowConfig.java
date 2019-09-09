package com.zdai.demo.email.config;

import com.zdai.demo.email.handler.EmailHandler;
import com.zdai.demo.email.pojo.EmailSenderProperties;
import com.zdai.demo.email.transformer.EmailTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;


@Configuration
public class EmailIntegrationFlowConfig {
    private final Logger logger = LoggerFactory.getLogger(EmailIntegrationFlowConfig.class);

    @Bean
    public IntegrationFlow myEmailFlow(
            EmailSenderProperties emailSenderProperties,
            EmailTransformer emailTransformer,
            EmailHandler emailHandler) {
        logger.info("============== Connecting to: {} ==============", emailSenderProperties.getImapUrl());
        return IntegrationFlows.from(Mail.imapInboundAdapter(emailSenderProperties.getImapUrl()),
                e->e.poller(Pollers.fixedDelay(Long.parseLong(emailSenderProperties.getPollRate()))))
                .transform(emailTransformer)
                .handle(emailHandler)
                .get();
    }
}
