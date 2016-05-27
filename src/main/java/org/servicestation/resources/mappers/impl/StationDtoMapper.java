package org.servicestation.resources.mappers.impl;

import org.servicestation.model.Station;
import org.servicestation.resources.dto.StationDto;
import org.servicestation.resources.mappers.IDtoMapper;

public class StationDtoMapper implements IDtoMapper<StationDto, Station> {

    @Override
    public Station mapDtoToServerObject(StationDto dto) {
        Station station = new Station();
        station.id = dto.id;
        station.address = dto.address;
        station.description = dto.description;
        station.longitude = dto.longitude;
        station.latitude = dto.latitude;

        return station;
    }

    @Override
    public StationDto mapServerObjectToDto(Station serverObj) {
        StationDto dto = new StationDto();
        dto.id = serverObj.id;
        dto.address = serverObj.address;
        dto.description = serverObj.description;
        dto.longitude = serverObj.longitude;
        dto.latitude = serverObj.latitude;

        return dto;
    }

    @Override
    public Class getDtoType() {
        return StationDto.class;
    }

    @Override
    public Class getServerObjectType() {
        return Station.class;
    }
}
