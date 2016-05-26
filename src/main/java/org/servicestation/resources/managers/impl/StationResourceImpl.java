package org.servicestation.resources.managers.impl;

import org.servicestation.model.Station;
import org.servicestation.resources.IStationResource;
import org.servicestation.resources.dto.StationDto;
import org.servicestation.resources.managers.IStationManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

public class StationResourceImpl implements IStationResource {

    @Autowired
    private IStationManager stationManager;

    @Override
    public Response createNewStation(@Context SecurityContext securityContext, Station station) {
        stationManager.createNewStation(station);
        return Response.ok().build();
    }

    @Override
    public Response updateStation(@Context SecurityContext securityContext, Station station, Integer stationId) throws Exception {
        stationManager.changeStation(stationId, station);
        return Response.ok().build();
    }

    @Override
    public List<StationDto> getAllIndexStations(@Context SecurityContext securityContext) {
        return stationManager.getStations().stream()
                .map(station -> new StationDto(station.id, station.latitude, station.longitude))
                .collect(Collectors.toList());
    }

    @Override
    public List<Station> getAllStations(@Context SecurityContext securityContext) {
        return stationManager.getStations();
    }

    @Override
    public Response deleteStation(@Context SecurityContext securityContext, Integer stationId) {
        stationManager.deleteStation(stationId);
        return Response.ok().build();
    }
}
