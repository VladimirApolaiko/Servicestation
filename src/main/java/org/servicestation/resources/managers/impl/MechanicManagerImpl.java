package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IMechanicDao;
import org.servicestation.model.Mechanic;
import org.servicestation.resources.dto.MechanicDto;
import org.servicestation.resources.managers.IMechanicManager;
import org.servicestation.resources.mappers.IObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class MechanicManagerImpl implements IMechanicManager {

    @Autowired
    private IMechanicDao mechanicDao;

    @Autowired
    private IObjectMapper mapper;

    @Override
    public MechanicDto createMechanic(MechanicDto mechanic) {
        return mapper.mapServerObjectToDto(mechanicDao.createMechanic(mapper.mapDtoToServerObject(mechanic)));
    }

    @Override
    public MechanicDto getMechanicById(Integer mechanicId) {
        return mapper.mapServerObjectToDto(mechanicDao.getMechanicById(mechanicId));
    }

    @Override
    public MechanicDto deleteMechanicById(Integer mechanicId) {
        return mapper.mapServerObjectToDto(mechanicDao.deleteMechanicById(mechanicId));
    }

    @Override
    public MechanicDto changeMechanic(Integer mechanicId, MechanicDto newMechanic) {
        return mapper.mapServerObjectToDto(mechanicDao.changeMechanic(mechanicId, mapper.mapDtoToServerObject(newMechanic)));
    }

    @Override
    public List<MechanicDto> getAllMechanics(Integer stationId) {
        return mechanicDao.getAllMechanics(stationId).stream()
                .map(mechanic -> mapper.<MechanicDto, Mechanic>mapServerObjectToDto(mechanic))
        .collect(Collectors.toList());
    }
}
