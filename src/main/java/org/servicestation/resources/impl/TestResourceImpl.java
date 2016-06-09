package org.servicestation.resources.impl;

import org.servicestation.dao.*;
import org.servicestation.model.Order;
import org.servicestation.model.Status;
import org.servicestation.resources.ITestResource;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.managers.IAuthoritiesManager;
import org.servicestation.resources.managers.IOrderManager;
import org.servicestation.resources.managers.impl.MailManager;
import org.servicestation.resources.mappers.IObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.google.common.primitives.Ints.asList;


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
    private IOrderManager orderManager;

    @Autowired
    private IAuthoritiesManager authoritiesManager;

    @Autowired
    private MailManager mailManager;

    @Autowired
    private IObjectMapper objectMapper;


    public String test() throws Exception {
        FullOrderDto fullOrderDto = new FullOrderDto();
        fullOrderDto.workDescription = "Some work description";
        fullOrderDto.status = Status.INIT.toString();
        fullOrderDto.stationId = 17;
        fullOrderDto.orderDate = "2016-06-01 14:40";
        fullOrderDto.carId = 11;
        fullOrderDto.servicesIds = asList(2,3);

        orderManager.createNewOrder("vladimirapolaiko@gmail.com", fullOrderDto);

        return "Success";
    }


}
