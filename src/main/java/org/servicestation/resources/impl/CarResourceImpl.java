package org.servicestation.resources.impl;

import org.servicestation.model.Car;
import org.servicestation.resources.ICarResource;
import org.servicestation.resources.dto.CarDto;
import org.servicestation.resources.exceptions.CarNotFoundException;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.managers.ICarManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

public class CarResourceImpl implements ICarResource{

    @Autowired
    private ICarManager carManager;

    @Override
    public CarDto createNewCar(SecurityContext securityContext, CarDto car) throws UserDoesNotExists {
        return carManager.createNewCar(securityContext.getUserPrincipal().getName(), car);
    }

    @Override
    public List<CarDto> getCars(SecurityContext securityContext) throws UserDoesNotExists, CarNotFoundException {
        return carManager.getCars(securityContext.getUserPrincipal().getName());
    }

    @Override
    public CarDto updateCar(SecurityContext securityContext, CarDto car, Integer carId) throws Exception {
        return carManager.changeCar(securityContext.getUserPrincipal().getName(), carId, car);
    }

    @Override
    public Response deleteCar(SecurityContext securityContext, Integer carId) throws UserDoesNotExists, CarNotFoundException {
        carManager.deleteCar(securityContext.getUserPrincipal().getName(), carId);
        return Response.ok().build();
    }
}
