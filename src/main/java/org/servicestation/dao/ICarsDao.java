package org.servicestation.dao;


import org.servicestation.model.Car;

import java.util.List;

public interface ICarsDao {
    Car createCar(final String username, final Car newCar);

    Car updateCar(final int carId, final Car newCar) throws Exception;

    Car deleteCar(final int carId);

    List<Car> getCarsByUsername(final String username);

}
