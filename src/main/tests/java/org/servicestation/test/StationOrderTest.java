package org.servicestation.test;

import org.junit.Test;
import org.servicestation.dao.IStationOrderDao;
import org.servicestation.dao.IOrderDao;
import org.servicestation.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class StationOrderTest extends AbstractTest {

    @Autowired
    private IStationOrderDao iMechanicOrder;

    @Autowired
    private IOrderDao iOrderDao;


    @Test
    public void assignOrder() {
        final int stationId = 1;
        final Long orderId = 3L;

        iMechanicOrder.assignOrder(stationId, orderId);
        List<Order> mechanicOrders = iOrderDao.getMechanicOrders(stationId);
        assertTrue(mechanicOrders.size() > 0);
        assertEquals(orderId, mechanicOrders.get(0).id);
    }

    @Test
    public void unAssignOrder() {
        final int stationId = 2;
        final Long orderId = 5L;

        iMechanicOrder.unAssignOrder(stationId, orderId);
        List<Order> mechanicOrders = iOrderDao.getMechanicOrders(stationId);
        assertEquals(0, mechanicOrders.size());
    }
}
