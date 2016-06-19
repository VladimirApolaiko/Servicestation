package org.servicestation.resources.managers.impl;

import org.servicestation.dao.ICarDao;
import org.servicestation.dao.IUserDao;
import org.servicestation.model.Car;
import org.servicestation.resources.dto.CarDto;
import org.servicestation.resources.exceptions.CarNotFoundException;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.managers.ICarManager;
import org.servicestation.resources.mappers.IObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.stream.Collectors;

public class CarManagerImpl implements ICarManager {

    @Autowired
    private ICarDao carDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IObjectMapper mapper;


    @Override
    public CarDto createNewCar(String username, CarDto newCar) throws UserDoesNotExists {
        try {
            userDao.getUserByUsername(username);

            return mapper.mapServerObjectToDto(carDao.createCar(username, mapper.mapDtoToServerObject(newCar)));
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username " + username + " does not exists");
        }
    }

    @Override
    public void deleteCar(String username, Integer carId) throws UserDoesNotExists, CarNotFoundException {
        try {
            userDao.getUserByUsername(username);

        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username " + username + " does not exists");
        }

        if (!isCarExists(username, carId)) {
            throw new CarNotFoundException("Car with id " + carId + " not found for user " + username);
        }


        carDao.deleteCar(carId);
    }

    @Override
    public List<CarDto> getCars(String username) throws UserDoesNotExists, CarNotFoundException {
        try {
            userDao.getUserByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username " + username);
        }
        return carDao.getCarsByUsername(username).stream()
                .map(car -> mapper.<CarDto, Car>mapServerObjectToDto(car))
                .collect(Collectors.toList());
    }

    @Override
    public CarDto changeCar(String username, Integer carId, CarDto newCar) throws UserDoesNotExists, CarNotFoundException {
        try {
            userDao.getUserByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username " + username);
        }

        if (!isCarExists(username, carId)) {
            throw new CarNotFoundException("Car with id " + carId + " not found for user " + username);
        }

        return mapper.mapServerObjectToDto(carDao.updateCar(carId, mapper.mapDtoToServerObject(newCar)));
    }

    private boolean isCarExists(String username, Integer carId) throws CarNotFoundException {
        List<Integer> carsByUsername;
        try {
            carsByUsername = carDao.getCarsByUsername(username).stream().map(car -> car.id).collect(Collectors.toList());
        } catch (EmptyResultDataAccessException e) {
            throw new CarNotFoundException("Car with id " + carId + " not found for user " + username);
        }
        return carsByUsername.contains(carId);
    }
}
