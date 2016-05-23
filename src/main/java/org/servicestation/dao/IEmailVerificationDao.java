package org.servicestation.dao;


import org.servicestation.model.EmailVerification;

public interface IEmailVerificationDao {
    EmailVerification createEmailVerificationToken(String username, String token);

    void deleteAllVerificationTokens(String username);

    EmailVerification getVerification(String username);
}
