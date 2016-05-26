package org.servicestation.test;

import org.junit.Test;
import org.servicestation.dao.ICarDao;
import org.servicestation.model.Car;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class CarsDaoTest extends AbstractTest {

    @Autowired
    private ICarDao carDao;

    @Test
    public void testCreateCar() {
        final String username = "test_user";
        final Car newCar = new Car();

        newCar.brand = "test_brand";
        newCar.model = "test_model";
        newCar.vin = "test_vin";
        newCar.registration_number = "КР3599";
        newCar.engine_volume = 2.0;

        Car car = carDao.createCar(username, newCar);

        assertEquals(newCar.brand, car.brand);
        assertEquals(newCar.model, car.model);
        assertEquals(newCar.vin, car.vin);
        assertEquals(newCar.registration_number, car.registration_number);
        assertEquals(newCar.engine_volume, car.engine_volume);
    }

    @Test
    public void testChangeCar() throws Exception {
        final int carId = 1;

        final Car newCar = new Car();
        newCar.brand = "test_change_car_brand";
        newCar.model = "test_change_car_model";
        newCar.vin = "test_change_car_vin";
        newCar.registration_number = "КР3599";
        newCar.engine_volume = 3.0;
        newCar.username = "test_user_foreign_3";

        Car car = carDao.updateCar(carId, newCar);

        assertEquals(newCar.brand, car.brand);
        assertEquals(newCar.model, car.model);
        assertEquals(newCar.vin, car.vin);
        assertEquals(newCar.registration_number, car.registration_number);
        assertEquals(newCar.engine_volume, car.engine_volume);
        assertEquals(newCar.username, car.username);
    }

    @Test
    public void testDeleteCar() {
        final Integer carId = 2;
        Car car = carDao.deleteCar(carId);

        assertEquals(carId, car.id);
    }

    @Test
    public void testGetAllCars() {
        final String username = "test_user_foreign_2";
        final int numberOfCars = 3;
        List<Car> cars = carDao.getCarsByUsername(username);

        assertEquals(numberOfCars, cars.size());
    }

}
