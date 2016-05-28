package org.servicestation.resources.managers;

import org.servicestation.resources.dto.MapStationDto;
import org.servicestation.resources.dto.StationDto;
import org.servicestation.resources.exceptions.StationDoesNotExists;

import java.util.List;

public interface IStationManager {
    StationDto createNewStation(StationDto newStation);

    void deleteStation(Integer stationId) throws StationDoesNotExists;

    List<StationDto> getStations();

    MapStationDto getMapStations();

    StationDto changeStation(Integer stationId, StationDto newStation) throws StationDoesNotExists;


}
