package org.servicestation.resources.impl;

import org.servicestation.dao.*;
import org.servicestation.model.*;
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
    private IUserDao userDao;

    @Autowired
    private IMechanicOrder mechanicOrderDao;

    public String test() throws Exception {
        /*Station DAO*/
       /* Station station = stationDao.createStation("Servicestation 1", "Sovetskaya st. 4", "Good servicestation", 54.3, 32.4);
        Station newStation = stationDao.changeStation(station.id, new Station() {{
            description = "Another description";
        }});

        *//*Mechanic DAO*//*
        Station stationGotById = stationDao.getStationById(newStation.id);
        List<Station> allStations = stationDao.getAllStations();


        Mechanic mechanic = mechanicDao.createMechanic("VladimirApolaiko", station.id);
        Mechanic newMechanic = mechanicDao.changeMechanic(mechanic.id, new Mechanic() {{
            nickname = "vladimirapolaiko";
        }});
        Mechanic mechanicGotById = mechanicDao.getMechanicById(newMechanic.id);
        List<Mechanic> allMechanics = mechanicDao.getAllMechanics(station.id);

        *//*MechanicProfile DAO*//*
        profileDao.createProfile(mechanic.id, "Vladimir", "Apolaiko", "Sergeevich", "KH2233432", "3323443234432", "g.Grodno, Tavlaya st. 34/3-27", "+375336878957");
        Mechanic mechanicWithProfile = mechanicDao.getMechanicById(mechanic.id);
        Profile profile = profileDao.changeProfile(mechanic.id, new Profile() {{
            phone_number = "7788";
        }});
        Profile MechanicsProfile = profileDao.getProfileById(mechanic.id);*/

        /*Order DAO*/
       /* Order newOrder = orderDao.createNewOrder();
        Order order = orderDao.changeOrder(newOrder.id, new Order() {{
            status = Status.DONE;
            station_id = 5L;
        }});
        Order orderById = orderDao.getOrderById(newOrder.id);
        orderDao.getAllOrders(5);

        mechanicOrderDao.assignOrder(12, order.id);

        orderDao.deleteOrder(order.id);
        mechanicDao.deleteMechanic(12);*/
        /*User user = new User();
        user.username = "vladimirapolaiko@gmail.com";
        user.password = "123456789";
        user.enabled = true;
        user.firstName = "Vladimir";
        user.lastName = "Apolaiko";
        User user1 = userDao.createUser(user);

        user.password = "hello";
        User user2 = userDao.changeUserByUsername(user.username, user);

        User userByUsername = userDao.getUserByUsername("Karina");*/

        User user3 = userDao.deleteUserByUsername("ddfse");

        return "Success";
    }
}
