package org.servicestation;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class TestMail {
    private static final String ENCODING = "UTF-8";

    public static void main(String[] args) throws MessagingException {
        final String subject = "Subject";
        final String content = "Vlad Niculcha web developer";
        final String smtpHost = "smtp.gmail.com";
        final String from = "heroku.servicestation@gmail.com";
        final String to = "rassel333@gmail.com";
        final String login = "heroku.servicestation@gmail.com";
        final String password = "se1500002464803";
        final String smtpPort = "587";


        Properties props = System.getProperties();
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.mime.charset", ENCODING);

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subject);
        msg.setContent(content, "text/html; charset=utf-8");

        Transport.send(msg);
    }

}
