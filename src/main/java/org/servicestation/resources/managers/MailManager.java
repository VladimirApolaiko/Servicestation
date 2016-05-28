package org.servicestation.resources.managers;


import java.util.List;
import java.util.Map;

public interface MailManager {
    void sendEmail(String to,
                   String subject,
                   String mailTemplate,
                   Map<String, Object> templateModel);

    void sendEmail(List<String> to,
                   String subject,
                   String mailTemplate,
                   Map<String, Object> templateModel);
}

