package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Configuration of the {@link EmailSender} (additional to the Spring mail configuration for {@link JavaMailSender} bean autoconfiguration).
 */
@Component
@ConfigurationProperties(prefix = "spring.mail")
@Getter
@Setter
public class MailProperties {

    /**
     * Email address that the email should be sent from.
     */
    private String from;

    private String host;
    private int port;
    private String username;
    private String password;
    private Smtp smtp = new Smtp();

    @Getter
    @Setter
    public static class Smtp {
        private boolean auth;
        private Starttls starttls = new Starttls();
    }

    @Getter
    @Setter
    public static class Starttls {
        private boolean enable;
    }
}
