package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IAdminStationDao;
import org.servicestation.resources.managers.IAdminStationManager;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminStationManagerImpl implements IAdminStationManager{

    @Autowired
    private IAdminStationDao adminStationDao;

    @Override
    public void assignAdmin(String username, Integer stationId) {
        adminStationDao.assignAdmin(username, stationId);
    }

    @Override
    public void unassignAdmin(String username, Integer stationId) {
        adminStationDao.unassignAdmin(username, stationId);
    }
}
