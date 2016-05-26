package org.servicestation.resources.impl;

import org.servicestation.model.Car;
import org.servicestation.resources.ICarResource;
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
    public Response createNewCar(SecurityContext securityContext, Car car) throws UserDoesNotExists {
        carManager.createNewCar(securityContext.getUserPrincipal().getName(), car);
        return Response.ok().build();
    }

    @Override
    public List<Car> getCars(SecurityContext securityContext) throws UserDoesNotExists, CarNotFoundException {
        List<Car> cars = carManager.getCars(securityContext.getUserPrincipal().getName());
        return cars;
        /*return cars.toArray(new Car[cars.size()]);*/
    }

    @Override
    public Response updateCar(SecurityContext securityContext, Car car, Integer carId) throws Exception {
        carManager.changeCar(securityContext.getUserPrincipal().getName(), carId, car );
        return Response.ok().build();
    }

    @Override
    public Response deleteCar(SecurityContext securityContext, Integer carId) throws UserDoesNotExists, CarNotFoundException {
        carManager.deleteCar(securityContext.getUserPrincipal().getName(), carId);
        return Response.ok().build();
    }
}
