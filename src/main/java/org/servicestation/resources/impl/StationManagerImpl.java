package org.servicestation.resources.impl;

import org.servicestation.dao.IStationDao;
import org.servicestation.model.Station;
import org.servicestation.resources.managers.IStationManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StationManagerImpl implements IStationManager{

    @Autowired
    private IStationDao stationDao;

    @Override
    public void createNewStation(Station newStation) {
        stationDao.createStation(newStation);
    }

    @Override
    public void deleteStation(Integer stationId) {
        stationDao.deleteStation(stationId);
    }

    @Override
    public List<Station> getStations() {
        return stationDao.getAllStations();
    }

    @Override
    public void changeStation(Integer stationId, Station newStation) throws Exception {
        stationDao.changeStation(stationId, newStation);
    }
}
