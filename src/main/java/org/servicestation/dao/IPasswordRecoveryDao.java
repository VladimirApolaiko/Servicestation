package org.servicestation.dao;

import org.servicestation.model.PasswordRecovery;

public interface IPasswordRecoveryDao {
    PasswordRecovery createPasswordRecoveryToken(String username, String token);

    PasswordRecovery deletePasswordRecoveryToken(String token);

    PasswordRecovery getPasswordRecoveryToken(String token);
}
