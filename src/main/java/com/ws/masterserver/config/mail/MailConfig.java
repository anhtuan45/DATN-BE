package com.ws.masterserver.config.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    private String HOST = "smtp.gmail.com";

    private Integer PORT = 587;

    private String USERNAME = "hungnnit98@gmail.com";

    private String PASSWORD = "hzoyobgtgndaumvl";

    private static final String PROTOCOL_KEY = "mail.transport.protocol";
    private static final String PROTOCOL_VALUE = "smtp";
    private static final String SMTP_AUTH_KEY = "mail.smtp.auth";
    private static final String SMTP_AUTH_VALUE = "true";
    private static final String STARTTLS_KEY = "mail.smtp.starttls.enable";
    private static final String STARTTLS_VALUE = "true";
    private static final String DEBUG_KEY = "mail.debug";
    private static final String DEBUG_VALUE = "true";

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(HOST);
        mailSender.setPort(PORT);
        mailSender.setUsername(USERNAME);
        mailSender.setPassword(PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put(STARTTLS_KEY, STARTTLS_VALUE);
        props.put(PROTOCOL_KEY, PROTOCOL_VALUE);
        props.put(SMTP_AUTH_KEY, SMTP_AUTH_VALUE);
        props.put(DEBUG_KEY, DEBUG_VALUE);
        return mailSender;
    }
}
