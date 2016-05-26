package org.servicestation.resources.managers;

import org.servicestation.model.Car;
import org.servicestation.resources.exceptions.CarNotFoundException;
import org.servicestation.resources.exceptions.UserDoesNotExists;

import java.util.List;

public interface ICarManager {
    void createNewCar(String username, Car newCar) throws UserDoesNotExists;

    void deleteCar(String username, Integer carId) throws UserDoesNotExists, CarNotFoundException;

    List<Car> getCars(String username) throws UserDoesNotExists, CarNotFoundException;

    void changeCar(String username, Integer carId, Car newCar) throws Exception;

}
