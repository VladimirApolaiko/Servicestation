package org.servicestation.dao;

import org.servicestation.model.Mechanic;
import org.servicestation.model.Profile;

import java.util.List;

public interface IMechanicDao {

    Mechanic createMechanic(final Mechanic mechanic);

    Mechanic getMechanicById(final Integer mechanicId);

    Mechanic deleteMechanicById(final Integer mechanicId);

    Mechanic changeMechanic(final Integer mechanicId, final Mechanic newMechanic);

    List<Mechanic> getAllMechanics(final Integer stationId);
}
