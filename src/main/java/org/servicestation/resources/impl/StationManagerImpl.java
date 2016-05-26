package org.servicestation.resources.impl;

import org.servicestation.dao.IStationDao;
import org.servicestation.model.Station;
import org.servicestation.resources.exceptions.StationDoesNotExists;
import org.servicestation.resources.managers.IStationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public class StationManagerImpl implements IStationManager{

    @Autowired
    private IStationDao stationDao;

    @Override
    public void createNewStation(Station newStation) {
        stationDao.createStation(newStation);
    }

    @Override
    public void deleteStation(Integer stationId) throws StationDoesNotExists {
        try{
            stationDao.getStationById(stationId);
            stationDao.deleteStation(stationId);
        }catch(EmptyResultDataAccessException e) {
            throw new StationDoesNotExists("Station with id " +  stationId + " does not exists");
        }
    }

    @Override
    public List<Station> getStations() {
        return stationDao.getAllStations();
    }

    @Override
    public void changeStation(Integer stationId, Station newStation) throws Exception {
        try{
            stationDao.getStationById(stationId);
            stationDao.changeStation(stationId, newStation);
        }catch(EmptyResultDataAccessException e) {
            throw new StationDoesNotExists("Station with id " +  stationId + " does not exists");
        }
    }
}
