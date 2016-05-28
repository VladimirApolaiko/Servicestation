package org.servicestation.dao;

import org.servicestation.model.Station;

import java.util.List;

public interface IStationDao {

    Station createStation(final Station newStation);

    Station getStationById(final Integer stationId);

    List<Station> getAllStations();

    Station changeStation(final Integer stationId, final Station newStation);

    Station deleteStation(final Integer stationId);
}
