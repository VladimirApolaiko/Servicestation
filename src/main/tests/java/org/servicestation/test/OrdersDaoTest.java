/*
package org.servicestation.test;


import org.junit.Test;
import org.servicestation.dao.IOrderDao;
import org.servicestation.model.Order;
import org.servicestation.model.Status;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrdersDaoTest extends AbstractTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Autowired
    private IOrderDao orderDao;

    @Test
    public void testCreateNewOrder() {
        Order newOrder = orderDao.createNewOrder();
        assertEquals(Status.INIT, newOrder.status);
    }

    @Test
    public void testChangeOrder() throws Exception {
        final long orderId = 1;
        final Order newOrder = new Order();
        newOrder.initial_date = LocalDate.now();
        newOrder.status = Status.ACCEPTED;
        newOrder.work_description = "test_change_order_work_description";
        newOrder.planned_cost = 30000.0;
        newOrder.planned_end_date = LocalDate.now();
        newOrder.end_date = LocalDate.now();
        newOrder.station_id = 1L;

        Order changedOrder = orderDao.changeOrder(orderId, newOrder);

        assertEquals(orderId, (long) changedOrder.id);
        assertEquals(DATE_TIME_FORMATTER.format(newOrder.initial_date),
                DATE_TIME_FORMATTER.format(changedOrder.initial_date));

        assertEquals(newOrder.status, changedOrder.status);
        assertEquals(newOrder.work_description, changedOrder.work_description);
        assertEquals(newOrder.planned_cost, changedOrder.planned_cost);

        assertEquals(DATE_TIME_FORMATTER.format(newOrder.planned_end_date),
                DATE_TIME_FORMATTER.format(changedOrder.planned_end_date));

        assertEquals(DATE_TIME_FORMATTER.format(newOrder.end_date),
                DATE_TIME_FORMATTER.format(changedOrder.end_date));
        assertEquals(newOrder.station_id, changedOrder.station_id);
    }

    @Test
    public void testDeleteOrder() {
        final long orderId = 2;

        Order deletedOrder = orderDao.deleteOrder(orderId);
        assertEquals(orderId, (long) deletedOrder.id);
    }

    @Test
    public void testGetOrderById() {
        final long orderId = 3;
        final LocalDate initialDate = LocalDate.of(2016, 1, 1);
        final String workDescription = "test_order";
        final Status status = Status.INIT;
        final Double plannedCost = 40000.0;
        final LocalDate plannedEndDate = LocalDate.of(2016, 1, 3);
        final Double totalCost = 0.0;
        final LocalDate endDate = LocalDate.of(2016, 1, 3);
        final Long stationId = 1L;

        Order order = orderDao.getOrderById(orderId);

        assertEquals(DATE_TIME_FORMATTER.format(initialDate),
                DATE_TIME_FORMATTER.format(order.initial_date));

        assertEquals(workDescription, order.work_description);
        assertEquals(status, order.status);
        assertEquals(plannedCost, order.planned_cost);

        assertEquals(DATE_TIME_FORMATTER.format(plannedEndDate),
                DATE_TIME_FORMATTER.format(order.planned_end_date));

        assertEquals(totalCost, order.total_cost);
        assertEquals(DATE_TIME_FORMATTER.format(endDate), DATE_TIME_FORMATTER.format(order.end_date));
        assertEquals(stationId, order.station_id);
    }

    @Test
    public void testGetAllOrdersByStationId() {
        final int stationId = 2;
        final int numberOfOrders = 2;
        List<Order> orders = orderDao.getAllOrders(stationId);
        assertEquals(numberOfOrders, orders.size());
    }

    @Test
    public void testGetMechanicOrders() {
        final int mechanicId = 1;
        final int numberOfOrders = 2;

        List<Order> mechanics = orderDao.getMechanicOrders(mechanicId);
        assertEquals(numberOfOrders, mechanics.size());
    }
}
*/
