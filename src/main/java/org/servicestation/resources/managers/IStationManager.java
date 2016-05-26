package org.servicestation.resources.managers;

import org.servicestation.model.Station;

import java.util.List;

public interface IStationManager {
    void createNewStation(Station newStation);

    void deleteStation(Integer stationId);

    List<Station> getStations();

    void changeStation(Integer stationId, Station newStation) throws Exception;


}
