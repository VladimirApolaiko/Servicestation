package org.servicestation.resources.impl;

import org.servicestation.dao.*;
import org.servicestation.model.Mechanic;
import org.servicestation.model.Order;
import org.servicestation.model.Profile;
import org.servicestation.model.Station;
import org.servicestation.resources.TestResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class TestResourceImpl implements TestResource {

    @Autowired
    private IStationDao stationDao;

    @Autowired
    private IMechanicDao mechanicDao;

    @Autowired
    private IProfileDao profileDao;

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IMechanicOrder mechanicOrderDao;

    public String test() throws Exception {
       /* *//*Mechanic mechanic = mechanicDao.createMechanic("Javva222883", 1);
        Profile profile = profileDao.createProfile(mechanic.id, "Vladimir", "Apolaiko", "Sergeevich", "KH22333998", "323432421234", "Sovetskaya 32/4", "+375336878957");
        Mechanic changeMechanic = mechanicDao.changeMechanic(mechanic.id, new Mechanic() {{
            nickname = "python";
        }});
        Profile profile1 = profileDao.changeProfile(mechanic.id, new Profile() {{
            phone_number = "+8885557777";
        }});

        Profile profileById = profileDao.getProfileById(mechanic.id);*//*

        Order order = orderDao.createNewOrder();

        orderDao.changeOrder(order.id, new Order(){{
            work_description = "very difficult case";
        }});*/
        Mechanic mechanic = null;
        try{
            mechanic = mechanicDao.createMechanic("Tim", 1);
            Order order = orderDao.createNewOrder();
            mechanicOrderDao.assignOrder(mechanic.id, order.id);
            mechanicOrderDao.getMechanicOrders(mechanic.id);

        }finally {
            if(mechanic != null){
                mechanicDao.deleteMechanic(mechanic.id);
            }
        }

        return "Successfully";
    }
}
