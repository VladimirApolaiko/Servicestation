package org.servicestation.dao;

import org.servicestation.model.Profile;

public interface IProfileDao {

    Profile changeProfile(final Integer mechanicId, final Profile newProfile) throws Exception;
    Profile getProfileById(final Integer mechanicId);
    Profile createProfile(final Integer mechanicId,
                          final String name,
                          final String surname,
                          final String patronymic,
                          final String passportNumber,
                          final String passportId,
                          final String address,
                          final String phoneNumber);
}
