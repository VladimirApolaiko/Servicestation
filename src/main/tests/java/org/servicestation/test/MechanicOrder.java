package org.servicestation.test;

import org.junit.Test;
import org.servicestation.dao.IMechanicOrder;
import org.servicestation.dao.IOrderDao;
import org.servicestation.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class MechanicOrder extends AbstractTest {

    @Autowired
    private IMechanicOrder iMechanicOrder;

    @Autowired
    private IOrderDao iOrderDao;


    @Test
    public void assignOrder() {
        final int mechanicId = 5;
        final Long orderId = 3L;

        iMechanicOrder.assignOrder(mechanicId, orderId);
        List<Order> mechanicOrders = iOrderDao.getMechanicOrders(mechanicId);
        assertTrue(mechanicOrders.size() > 0);
        assertEquals(orderId, mechanicOrders.get(0).id);
    }

    @Test
    public void unAssignOrder() {
        final int mechanicId = 2;
        final Long orderId = 5L;

        iMechanicOrder.unAssignOrder(mechanicId, orderId);
        List<Order> mechanicOrders = iOrderDao.getMechanicOrders(mechanicId);
        assertEquals(0, mechanicOrders.size());
    }
}
