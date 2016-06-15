package org.servicestation.resources.impl;

import org.servicestation.resources.IMechanicResource;
import org.servicestation.resources.dto.MechanicDto;
import org.servicestation.resources.managers.IMechanicManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MechanicResourceImpl implements IMechanicResource{

    @Autowired
    private IMechanicManager mechanicManager;

    @Override
    public MechanicDto createMechanic(MechanicDto dto) {
        return mechanicManager.createMechanic(dto);
    }

    @Override
    public MechanicDto getMechanicById(Integer mechanicId) {
        return mechanicManager.getMechanicById(mechanicId);
    }

    @Override
    public void deleteMechanicById(Integer mechanicId) {
        mechanicManager.deleteMechanicById(mechanicId);
    }

    @Override
    public MechanicDto changeMechanicById(Integer mechanicId, MechanicDto newMechanic) {
        return mechanicManager.changeMechanic(mechanicId, newMechanic);
    }

    @Override
    public List<MechanicDto> getAllMechanicsByStationId(Integer stationId) {
        return mechanicManager.getAllMechanics(stationId);
    }
}
