package org.servicestation.dao;

import org.servicestation.model.Station;

import java.util.List;

public interface IStationDao {

    Station createStation(String name,
                          String address,
                          String description,
                          double latitude,
                          double longitude);

    Station getStationById(int stationId);

    List<Station> getAllStations();

    Station changeStation(int stationId, Station newStation);

    Station deleteStation(int stationId);
}
