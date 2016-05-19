package org.servicestation.resources.managers;


import javax.mail.MessagingException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface MailManager {
    void sendEmail(String to,
                   String subject,
                   String mailTemplate,
                   Map<String, Object> templateModel) throws MessagingException;

    void sendEmail(List<String> to,
                   String subject,
                   String mailTemplate,
                   Map<String, Object> templateModel) throws MessagingException;
}
