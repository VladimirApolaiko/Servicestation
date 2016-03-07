package org.servicestation.dao;

import org.servicestation.model.Mechanic;
import org.servicestation.model.Profile;

import java.util.List;

public interface IMechanicDao {
    Mechanic createMechanic(final String nickname, final Integer stationId);

    Mechanic getMechanicById(final Integer mechanicId);

    Mechanic deleteMechanic(final Integer mechanicId);

    Mechanic changeMechanic(final Integer mechanicId, final Mechanic newMechanic) throws Exception;

    List<Mechanic> getAllMechanics(final Integer stationId);
}
