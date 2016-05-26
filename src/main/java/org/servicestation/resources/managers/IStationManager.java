package org.servicestation.resources.managers;

import org.servicestation.model.Station;
import org.servicestation.resources.exceptions.StationDoesNotExists;

import java.util.List;

public interface IStationManager {
    void createNewStation(Station newStation);

    void deleteStation(Integer stationId) throws StationDoesNotExists;

    List<Station> getStations();

    void changeStation(Integer stationId, Station newStation) throws Exception;


}
