package org.servicestation.dao;


import org.servicestation.model.Car;

import java.util.List;

public interface ICarDao {
    Car createCar(final String username, final Car newCar);

    Car updateCar(final int carId, final Car newCar);

    Car deleteCar(final int carId);

    List<Car> getCarsByUsername(final String username);

}
