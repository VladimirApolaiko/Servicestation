package org.servicestation.resources.dto;

import org.servicestation.resources.mappers.MapperManager;

public class CarDto {
    public Integer id;
    public String brand;
    public String model;
    public Double engine_volume;
    public String vin;
    public String registration_number;
    public String username;

    public static void main(String[] args) {
        MapperManager mapper = new MapperManager() {
            @Override
            public <T, E> T mapDtoToServerObject(E object) {
                return null;
            }

            @Override
            public <E, T> E mapServerObjectToDto(T object) {
                return null;
            }
        };
    }
}
