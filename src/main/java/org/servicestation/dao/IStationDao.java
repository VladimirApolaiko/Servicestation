package org.servicestation.dao;

import org.servicestation.model.Station;

import java.util.List;

public interface IStationDao {

    Station createStation(final String name,
                          final String address,
                          final String description,
                          final Double latitude,
                          final Double longitude);

    Station getStationById(final Integer stationId);

    List<Station> getAllStations();

    Station changeStation(final Integer stationId, final Station newStation) throws Exception;

    Station deleteStation(final Integer stationId);
}
