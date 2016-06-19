package org.servicestation.resources.mappers.impl;

import org.servicestation.model.Car;
import org.servicestation.resources.dto.CarDto;
import org.servicestation.resources.mappers.IDtoMapper;

public class CarDtoMapper implements IDtoMapper<CarDto, Car>{

    @Override
    public Car mapDtoToServerObject(CarDto dto) {
        Car car = new Car();
        car.brand = dto.brand;
        car.model = dto.model;
        car.engine_volume = dto.engine_volume;
        car.vin = dto.vin;
        car.registration_number = dto.registration_number;

        return car;
    }

    @Override
    public CarDto mapServerObjectToDto(Car serverObj) {
        CarDto carDto = new CarDto();
        carDto.id = serverObj.id;
        carDto.brand = serverObj.brand;
        carDto.model = serverObj.model;
        carDto.engine_volume = serverObj.engine_volume;
        carDto.vin = serverObj.vin;
        carDto.registration_number = serverObj.registration_number;

        return carDto;
    }

    @Override
    public Class getDtoType() {
        return CarDto.class;
    }

    @Override
    public Class getServerObjectType() {
        return Car.class;
    }
}
