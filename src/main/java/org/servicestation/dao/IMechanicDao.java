package org.servicestation.dao;

import org.servicestation.model.Mechanic;
import org.servicestation.model.Profile;

import java.util.List;

public interface IMechanicDao {

    Mechanic createMechanic(final String username, final Integer stationId);

    Mechanic getMechanicByUsername(final String username);

    Mechanic deleteMechanic(final String username);

    Mechanic changeMechanic(final Integer mechanicId, final Mechanic newMechanic) throws Exception;

    List<Mechanic> getAllMechanics(final Integer stationId);
}
