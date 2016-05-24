package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IPasswordRecoveryDao;
import org.servicestation.model.PasswordRecovery;
import org.servicestation.resources.exceptions.*;
import org.servicestation.resources.managers.IPasswordRecoveryManager;
import org.servicestation.resources.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PasswordRecoveryManagerImpl implements IPasswordRecoveryManager {

    private static final String PASSWORD_RECOVERY_TEMPLATE = "mail-templates/PasswordRecovery.vm";

    private static final int MIN_TOKEN_SIZE = 32;

    @Value("${platformUrl}")
    private String platformUrl;

    @Value("${passwordRecoveryTokenDaysExpire}")
    private int passwordRecoveryTokenDaysExpire;

    @Autowired
    private IPasswordRecoveryDao passwordRecoveryDao;

    @Autowired
    private MailManager mailManager;

    @Autowired
    private UserManager userManager;

    @Override
    public void recoverPassword(String token, String password, String confirmation) throws PasswordRecoveryTokenException {
        try {
            PasswordRecovery passwordRecovery = passwordRecoveryDao.getPasswordRecoveryToken(token);

            if (((java.sql.Date)passwordRecovery.created).toLocalDate().plusDays(passwordRecoveryTokenDaysExpire).compareTo(LocalDate.now()) < 0) {
                throw new PasswordRecoveryTokenException("Password Recovery token expired, please try again");
            }
            userManager.changeUserPassword(passwordRecovery.username, password, confirmation);
        }catch(EmptyResultDataAccessException e) {
            throw new PasswordRecoveryTokenException("Password Recovery token not found");
        }catch (UserDoesNotExists | AccessDeniedException | ValidationException e) {
            throw new PasswordRecoveryTokenException(e.getMessage());
        }
    }

    @Override
    public void sendPasswordRecoveryEmail(String username) {
        String passwordRecoveryVerificationToken = Utils.generateRandomString(MIN_TOKEN_SIZE);
        passwordRecoveryDao.createPasswordRecoveryToken(username, passwordRecoveryVerificationToken);

        Map<String, Object> passwordRecoveryTemplateParameters = new HashMap<>();
        passwordRecoveryTemplateParameters.put("platformUrl", platformUrl);
        passwordRecoveryTemplateParameters.put("token", passwordRecoveryVerificationToken);
        passwordRecoveryTemplateParameters.put("expireHours", passwordRecoveryTokenDaysExpire * 24);

        mailManager.sendEmail(username, "Password Recovery", PASSWORD_RECOVERY_TEMPLATE, passwordRecoveryTemplateParameters);
    }
}
