package org.servicestation.resources.managers;

import org.servicestation.model.AdminStation;

public interface IAdminStationManager {
    void assignAdmin(String username, Integer stationId);

    void unassignAdmin(String username);

    AdminStation getAdminStationByUsername(String username);
}
