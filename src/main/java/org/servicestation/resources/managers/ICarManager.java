package org.servicestation.resources.managers;

import org.servicestation.resources.dto.CarDto;
import org.servicestation.resources.exceptions.CarNotFoundException;
import org.servicestation.resources.exceptions.UserDoesNotExists;

import java.util.List;

public interface ICarManager {
    CarDto createNewCar(String username, CarDto newCar) throws UserDoesNotExists;

    void deleteCar(String username, Integer carId) throws UserDoesNotExists, CarNotFoundException;

    List<CarDto> getCars(String username) throws UserDoesNotExists, CarNotFoundException;

    CarDto changeCar(String username, Integer carId, CarDto newCar) throws Exception;

}
