package org.servicestation.test;

import org.servicestation.dao.IOrderDao;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class StationOrderTest extends AbstractTest {


    @Autowired
    private IOrderDao iOrderDao;


    /*@Test
    public void assignOrder() {
        final int station_id = 1;
        final Long orderId = 3L;
        final String timestamp = "2016-05-30 16:20";

        iMechanicOrder.assignOrder(station_id, orderId, timestamp);
        List<Order> mechanicOrders = iOrderDao.getMechanicOrders(station_id);
        assertTrue(mechanicOrders.size() > 0);
        assertEquals(orderId, mechanicOrders.get(0).id);
    }

    @Test
    public void unAssignOrder() {
        final int station_id = 2;
        final Long orderId = 5L;
        final String timestamp = "2016-05-30 16:20";

        iMechanicOrder.unAssignOrder(station_id, orderId);
        List<Order> mechanicOrders = iOrderDao.getMechanicOrders(station_id);
        assertEquals(0, mechanicOrders.size());
    }*/
}
