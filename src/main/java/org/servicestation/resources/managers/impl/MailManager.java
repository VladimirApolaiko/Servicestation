package org.servicestation.resources.managers.impl;


import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.velocity.VelocityEngineUtils;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class MailManager implements org.servicestation.resources.managers.MailManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailManager.class);

    @Value("${mail.smtp.port}")
    private String smtpPort;

    @Value("${mail.smtp.host}")
    private String smtpHost;

    @Value("${mail.smtp.auth}")
    private String smtpAuth;

    @Value("${mail.smtp.starttls.enable}")
    private String startTls;

    @Value("${mail.smtp.login}")
    private String login;

    @Value("${mail.smtp.password}")
    private String password;

    @Value("${mail.mime.charset}")
    private String encoding;

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public void sendEmail(String to,
                          String subject,
                          String mailTemplate,
                          Map<String, Object> templateModel) {

        sendMessages(Collections.singletonList(to), subject, mailTemplate, templateModel);
    }

    @Override
    public void sendEmail(List<String> to,
                          String subject,
                          String mailTemplate,
                          Map<String, Object> templateModel) {
        sendMessages(to, subject, mailTemplate, templateModel);
    }

    private void sendMessages(List<String> to,
                              String subject,
                              String mailTemplate,
                              Map<String, Object> templateModel)  {

        Properties props = new Properties();
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", startTls);
        props.put("mail.mime.charset", encoding);

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(login));
            List<InternetAddress> emailAddresses = to.stream().map(s -> {
                try {
                    return new InternetAddress(s);
                } catch (AddressException e) {
                    LOGGER.info("Incorrect email address");
                }
                return null;
            }).filter(internetAddress -> internetAddress != null).collect(Collectors.toList());

            InternetAddress[] addresses = new InternetAddress[emailAddresses.size()];
            msg.setRecipients(Message.RecipientType.TO, emailAddresses.toArray(addresses));
            msg.setSubject(subject);

            String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, mailTemplate, encoding, templateModel);

            msg.setContent(body, "text/html; charset=UTF-8");

            Transport.send(msg);

        } catch(MessagingException e) {
            LOGGER.warn("Failed attempt to send email for users {}", to.toString());
        }

    }
}
