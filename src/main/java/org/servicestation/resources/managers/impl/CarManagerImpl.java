package org.servicestation.resources.managers.impl;

import org.servicestation.dao.ICarDao;
import org.servicestation.dao.IUserDao;
import org.servicestation.model.Car;
import org.servicestation.resources.exceptions.CarNotFoundException;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.managers.ICarManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.stream.Collectors;

public class CarManagerImpl implements ICarManager {

    @Autowired
    private ICarDao carDao;

    @Autowired
    private IUserDao userDao;


    @Override
    public void createNewCar(String username, Car newCar) throws UserDoesNotExists {
        try {
            userDao.getUserByUsername(username);
            newCar.id = null;
            newCar.username = null;

            carDao.createCar(username, newCar);
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
    public List<Car> getCars(String username) throws UserDoesNotExists, CarNotFoundException {
        try {
            userDao.getUserByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username " + username);
        }
        return carDao.getCarsByUsername(username);
    }

    @Override
    public void changeCar(String username, Integer carId, Car newCar) throws Exception {
        try {
            userDao.getUserByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username " + username);
        }

        if (!isCarExists(username, carId)) {
            throw new CarNotFoundException("Car with id " + carId + " not found for user " + username);
        }

        carDao.updateCar(carId, newCar);
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
