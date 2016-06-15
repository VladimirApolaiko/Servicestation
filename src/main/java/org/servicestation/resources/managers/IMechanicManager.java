package org.servicestation.resources.managers;

import org.servicestation.resources.dto.MechanicDto;

import java.util.List;

public interface IMechanicManager {
    MechanicDto createMechanic(final MechanicDto mechanic);

    MechanicDto getMechanicById(final Integer mechanicId);

    MechanicDto deleteMechanicById(final Integer mechanicId);

    MechanicDto changeMechanic(final Integer mechanicId, final MechanicDto newMechanic);

    List<MechanicDto> getAllMechanics(final Integer stationId);
}
