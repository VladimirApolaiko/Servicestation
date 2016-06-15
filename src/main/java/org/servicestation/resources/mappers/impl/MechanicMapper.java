package org.servicestation.resources.mappers.impl;

import org.servicestation.model.Mechanic;
import org.servicestation.resources.dto.MechanicDto;
import org.servicestation.resources.mappers.IDtoMapper;

public class MechanicMapper implements IDtoMapper<MechanicDto, Mechanic>{

    @Override
    public Mechanic mapDtoToServerObject(MechanicDto dto) {
        Mechanic mechanic = new Mechanic();
        mechanic.firstname = dto.firstname;
        mechanic.lastname = dto.lastname;
        mechanic.station_id = dto.stationId;

        return mechanic;
    }

    @Override
    public MechanicDto mapServerObjectToDto(Mechanic serverObj) {
        MechanicDto dto = new MechanicDto();
        dto.firstname = serverObj.firstname;
        dto.lastname = serverObj.lastname;
        dto.stationId = serverObj.station_id;

        return dto;
    }

    @Override
    public Class getDtoType() {
        return MechanicDto.class;
    }

    @Override
    public Class getServerObjectType() {
        return Mechanic.class;
    }
}
