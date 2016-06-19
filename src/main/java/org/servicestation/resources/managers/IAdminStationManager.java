package org.servicestation.resources.managers;

public interface IAdminStationManager {
    void assignAdmin(String username, Integer stationId);

    void unassignAdmin(String username, Integer stationId);
}
