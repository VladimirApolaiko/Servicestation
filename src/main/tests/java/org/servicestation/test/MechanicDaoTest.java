/*
package org.servicestation.test;

import org.junit.Test;
import org.servicestation.dao.IMechanicDao;
import org.servicestation.model.Mechanic;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class MechanicDaoTest extends AbstractTest {

    @Autowired
    private IMechanicDao iMechanicDao;

    @Test
    public void createMechanic() {
        final String username = "test_user";
        final int stationId = 1;

        Mechanic mechanic = iMechanicDao.createMechanic(username, stationId);
        assertEquals(username, mechanic.username);
        assertEquals(stationId, (long) mechanic.station_id);
    }

    @Test
    public void getMechanicByUsername() {
        final String username = "test_user_foreign_1";
        final int stationId = 1;

        Mechanic mechanic = iMechanicDao.getMechanicByUsername(username);
        assertEquals(stationId, (long) mechanic.station_id);
        assertEquals(username, mechanic.username);
    }

    @Test
    public void changeMechanic() throws Exception {
        final int mechanicToChangeId = 2;
        final int newStationId = 3;

        Mechanic newMechanic = new Mechanic();
        newMechanic.station_id = newStationId;

        Mechanic mechanic = iMechanicDao.changeMechanic(mechanicToChangeId, newMechanic);
        //TODO compare other fields
        assertEquals(newStationId, (long) mechanic.station_id);
    }

    @Test
    public void getAllMechanics() {
        final int stationId = 2;
        final int numberOfMechanics = 2;

        List<Mechanic> mechanics = iMechanicDao.getAllMechanics(stationId);
        for (Mechanic mechanic : mechanics) {
            assertEquals(stationId, (long) mechanic.station_id);
        }

        assertEquals(mechanics.get(0).username, "test_user_foreign_4");
        assertEquals(mechanics.get(1).username, "test_user_foreign_5");

        assertEquals(numberOfMechanics, mechanics.size());
    }

    @Test
    public void deleteMechanic() {
        final String username = "test_user_foreign_2";

        Mechanic mechanic = iMechanicDao.deleteMechanic(username);
        assertEquals(username, mechanic.username);
    }


}
*/
