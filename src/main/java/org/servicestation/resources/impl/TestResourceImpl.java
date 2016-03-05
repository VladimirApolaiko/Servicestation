package org.servicestation.resources.impl;

import org.servicestation.dao.IMechanicDao;
import org.servicestation.dao.IStationDao;
import org.servicestation.model.Mechanic;
import org.servicestation.model.Profile;
import org.servicestation.model.Station;
import org.servicestation.resources.TestResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class TestResourceImpl implements TestResource {

    @Autowired
    private IStationDao stationDao;

    @Autowired
    private IMechanicDao mechanicDao;

    public String test() {
        /*Station newStation = stationDao.createStation("service_station_1", "Tavlaya 34/3-27", "It's servicestation", 23.824951, 53.678337);
        stationDao.getStationById(2);
        stationDao.changeStation(2, new Station() {{
            name = "COOL SERVICE";
        }});
        stationDao.getStationById(2);
        stationDao.deleteStation(2);
        return newStation.toString();*/
        Mechanic mechanic = mechanicDao.createMechanic("Vladimir", "Apolaiko", "Sergeevich", new Profile(){{phoneNumber = "+375336878957";}}, 5);
        return mechanic.toString();
    }
}
