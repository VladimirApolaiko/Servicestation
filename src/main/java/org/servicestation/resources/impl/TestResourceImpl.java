package org.servicestation.resources.impl;

import org.servicestation.dao.*;
import org.servicestation.resources.ITestResource;
import org.servicestation.resources.managers.IAuthoritiesManager;
import org.servicestation.resources.managers.impl.MailManager;
import org.springframework.beans.factory.annotation.Autowired;


public class TestResourceImpl implements ITestResource {

    @Autowired
    private IStationDao stationDao;

    @Autowired
    private IMechanicDao mechanicDao;

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IAuthoritiesDao iAuthoritiesDao;

    @Autowired
    private IStationOrderDao stationOrderDao;

    @Autowired
    private IAuthoritiesManager authoritiesManager;

    @Autowired
    private MailManager mailManager;

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
            username = "vladimirapolaiko";
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
    /*    User user = new User();
        user.username = "vladimirapolaiko@gmail.com";
        user.password = "123456789";
        user.enabled = true;
        user.firstname = "Vladimir";
        user.lastname = "Apolaiko";
        User user1 = userDao.createUser(user);*/

       /* user.password = "hello";
        User user2 = userDao.changeUserByUsername(user.username, user);

        User userByUsername = userDao.getUserByUsername("Karina");*/

        /*iAuthoritiesDao.grantAuthority("vvvv", Authority.ROLE_MECHANIC);*/
        /*iAuthoritiesDao.revokeAuthority("vladimir", Authority.ROLE_MECHANIC);*/
        /*iAuthoritiesDao.getAuthoritiesByUsername("vladimir");*/
        /*mailManager.sendEmail("vladimirapolaiko@gmail.com", "Hello", "mail-templates/VerifyEmail.vm", new HashMap<String, Object>(){{put("platformUrl", "hello");put("token", "some token");}});*/

        stationOrderDao.assignOrder(16, 1L, "2016-05-30 16:20");
        stationOrderDao.assignOrder(16, 2L, "2016-05-30 16:40");
        stationOrderDao.assignOrder(16, 3L, "2016-05-30 17:00");
        stationOrderDao.assignOrder(16, 4L, "2016-05-30 17:20");
        stationOrderDao.assignOrder(16, 5L, "2016-05-30 17:40");

        stationOrderDao.getStationOrders(16);
        /*stationOrderDao.unAssignOrder(16, 1L);*/

        return "Success";
    }
}
