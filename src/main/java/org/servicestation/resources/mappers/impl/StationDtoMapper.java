package org.servicestation.resources.mappers.impl;

import org.servicestation.model.Station;
import org.servicestation.resources.dto.StationDto;
import org.servicestation.resources.mappers.IDtoMapper;
import org.servicestation.resources.utils.Utils;

public class StationDtoMapper implements IDtoMapper<StationDto, Station> {

    @Override
    public Station mapDtoToServerObject(StationDto dto) {
        Station station = new Station();
        station.station_name = dto.station_name;
        station.address = dto.address;
        station.description = dto.description;
        station.longitude = dto.longitude;
        station.latitude = dto.latitude;
        station.working_hours = Utils.transformWorkingTimeToDatabaseRange(dto.working_hours);
        station.weekends_working_hours = Utils.transformWorkingTimeToDatabaseRange(dto.weekends_working_hours);

        return station;
    }

    @Override
    public StationDto mapServerObjectToDto(Station serverObj) {
        StationDto dto = new StationDto();
        dto.id = serverObj.id;
        dto.station_name = serverObj.station_name;
        dto.address = serverObj.address;
        dto.description = serverObj.description;
        dto.longitude = serverObj.longitude;
        dto.latitude = serverObj.latitude;
        dto.working_hours = Utils.transformWorkingTimeToDtoRange(serverObj.working_hours);
        dto.weekends_working_hours = Utils.transformWorkingTimeToDtoRange(serverObj.weekends_working_hours);

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
