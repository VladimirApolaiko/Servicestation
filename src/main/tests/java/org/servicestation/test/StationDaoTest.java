package org.servicestation.test;

import org.junit.Test;
import org.servicestation.dao.IStationDao;
import org.servicestation.model.Station;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class StationDaoTest extends AbstractTest {

    @Autowired
    private IStationDao iStationDao;

    @Test
    public void testCreateNewStation() {
        final Station station = new Station();
        station.station_name = "test_new_station_name";
        station.address = "test_new_station_address";
        station.description = "test_new_station_description";
        station.latitude = 20.0;
        station.longitude = 20.0;

        final Station createdStation = iStationDao.createStation(station);

        assertEquals(station.station_name, createdStation.station_name);
        assertEquals(station.address, createdStation.address);
        assertEquals(station.description, createdStation.description);
        assertEquals(station.latitude, createdStation.latitude);
        assertEquals(station.longitude, createdStation.longitude);
    }

    @Test
    public void testGetStation() {
        final int stationId = 3;
        final String name = "test_station";
        final String address = "address3";
        final String description = "description3";
        final Double latitude = 2.0;
        final Double longitude = 2.0;

        Station station = iStationDao.getStationById(stationId);
        assertEquals(stationId, (long) station.id);
        assertEquals(name, station.station_name);
        assertEquals(address, station.address);
        assertEquals(description, station.description);
        assertEquals(latitude, station.latitude);
        assertEquals(longitude, station.longitude);
    }

    @Test
    public void testChangeStation() throws Exception {
        final int stationId = 4;

        final Station newStation = new Station();
        newStation.station_name = "test_change_station";
        newStation.address = "test_change_station_address";
        newStation.description = "test_change_station_description";
        newStation.latitude = 100.0;
        newStation.longitude = 100.0;

        Station changedStation = iStationDao.changeStation(stationId, newStation);

        assertEquals(newStation.station_name, changedStation.station_name);
        assertEquals(newStation.address, changedStation.address);
        assertEquals(newStation.description, changedStation.description);
        assertEquals(newStation.latitude, changedStation.latitude);
        assertEquals(newStation.longitude, changedStation.longitude);
    }

    @Test
    public void testDeleteStation() {
        final int stationId = 5;
        final String name = "test_delete_station";
        final String address = "address5";
        final String description = "description5";
        final Double latitude = 4.0;
        final Double longitude = 4.0;

        Station deletedStation = iStationDao.deleteStation(stationId);

        assertEquals(name, deletedStation.station_name);
        assertEquals(address, deletedStation.address);
        assertEquals(description, deletedStation.description);
        assertEquals(latitude, deletedStation.latitude);
        assertEquals(longitude, deletedStation.longitude);
    }

    @Test
    /*This test depends on testDeleteStation and testCreateNewStation*/
    public void getAllStations() {
        final int expectedNumberOfStations = 5;
        List<Station> allStations = iStationDao.getAllStations();
        assertEquals(expectedNumberOfStations, allStations.size());
    }
}
