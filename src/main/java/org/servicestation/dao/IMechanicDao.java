package org.servicestation.dao;

import org.servicestation.model.Mechanic;
import org.servicestation.model.Profile;

public interface IMechanicDao {
    Mechanic createMechanic(String name,
                            String surname,
                            String patronymic,
                            Profile profile,
                            int stationId);

    Profile changeProfile(int mechanicId, Profile newProfile);

    Mechanic getMechanicById(int mechanicId);

    Mechanic deleteMechanic(int deleteMechanic);

}
