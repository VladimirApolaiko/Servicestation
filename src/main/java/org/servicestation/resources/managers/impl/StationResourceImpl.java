package org.servicestation.resources.managers.impl;

import org.servicestation.resources.IStationResource;
import org.servicestation.resources.dto.MapStationDto;
import org.servicestation.resources.dto.StationDto;
import org.servicestation.resources.exceptions.StationDoesNotExists;
import org.servicestation.resources.managers.IStationManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

public class StationResourceImpl implements IStationResource {

    @Autowired
    private IStationManager stationManager;

    @Override
    public StationDto createNewStation(SecurityContext securityContext, StationDto station) {
        return stationManager.createNewStation(station);
    }

    @Override
    public StationDto updateStation(SecurityContext securityContext, StationDto station, Integer stationId) throws Exception {
        return stationManager.changeStation(stationId, station);
    }

    @Override
    public MapStationDto getAllIndexStations(SecurityContext securityContext) {
        return stationManager.getMapStations();
    }

    @Override
    public List<StationDto> getAllStations(SecurityContext securityContext) {
        return stationManager.getStations();
    }

    @Override
    public Response deleteStation(SecurityContext securityContext, Integer stationId) throws StationDoesNotExists {
        stationManager.deleteStation(stationId);
        return Response.ok().build();
    }
}
