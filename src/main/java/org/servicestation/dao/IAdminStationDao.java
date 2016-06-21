package org.servicestation.dao;


import org.servicestation.model.AdminStation;

import java.util.List;

public interface IAdminStationDao {

    void assignAdmin(String username, Integer stationId);

    void unassignAdmin(String username, Integer stationId);

    AdminStation getAdminStationByUsername(String username);
}
