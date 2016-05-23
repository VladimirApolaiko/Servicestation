package org.servicestation.resources.managers.impl;


import org.apache.commons.lang.RandomStringUtils;
import org.servicestation.dao.IEmailVerificationDao;
import org.servicestation.dao.IUserDao;
import org.servicestation.model.EmailVerification;
import org.servicestation.model.User;
import org.servicestation.resources.exceptions.EmailConfirmationTokenException;
import org.servicestation.resources.managers.EmailVerificationManager;
import org.servicestation.resources.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailVerificationManagerImpl implements EmailVerificationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVerificationManagerImpl.class);

    private static final String VERIFICATION_EMAIL_TEMPLATE = "mail-templates/VerifyEmail.vm";

    private static final int MIN_TOKEN_SIZE = 32;

    @Value("${platformUrl}")
    private String platformUrl;

    @Value("${emailConfirmationTokenDaysExpire}")
    private int emailConfirmationTokenDaysExpire;

    @Autowired
    private IEmailVerificationDao emailVerificationDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private org.servicestation.resources.managers.MailManager mailManager;

    @Override
    public void verifyEmail(String token) throws EmailConfirmationTokenException {
        try {
            EmailVerification emailVerification = emailVerificationDao.getVerification(token);

            if (((java.sql.Date)emailVerification.created).toLocalDate().plusDays(emailConfirmationTokenDaysExpire).compareTo(LocalDate.now()) < 0) {
                throw new EmailConfirmationTokenException("Email Confirmation token expired, please try again");
            }

            User newUser = new User();
            newUser.enabled = true;
            userDao.changeUserByUsername(emailVerification.username, newUser);
        } catch (EmptyResultDataAccessException e) {
            throw new EmailConfirmationTokenException("Email confirmation token not found");
        }
    }

    @Override
    public void sendEmailConfirmation(String username) {
        emailVerificationDao.deleteAllVerificationTokens(username);
        String emailVerificationToken = generateEmailVerificationToken();
        emailVerificationDao.createEmailVerificationToken(username, emailVerificationToken);

        Map<String, Object> verificationEmailTemplateParameters = new HashMap<>();
        verificationEmailTemplateParameters.put("platformUrl", platformUrl);
        verificationEmailTemplateParameters.put("token", emailVerificationToken);
        verificationEmailTemplateParameters.put("expireHours", emailConfirmationTokenDaysExpire * 24);

        mailManager.sendEmail(username, "Email Confirmation", VERIFICATION_EMAIL_TEMPLATE, verificationEmailTemplateParameters);
    }

    private String generateEmailVerificationToken() {
        return RandomStringUtils.randomAlphanumeric(MIN_TOKEN_SIZE + (int) (Math.random() * 32));
    }

}
