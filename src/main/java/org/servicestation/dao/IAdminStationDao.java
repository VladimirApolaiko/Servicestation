package org.servicestation.dao;


public interface IAdminStationDao {

    void assignAdmin(String username, Integer stationId);

    void unassignAdmin(String username, Integer stationId);
}
