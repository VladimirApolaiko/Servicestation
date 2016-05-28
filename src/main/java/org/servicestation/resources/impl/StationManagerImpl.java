package org.servicestation.resources.impl;

import org.servicestation.dao.IStationDao;
import org.servicestation.model.Station;
import org.servicestation.resources.dto.MapStationDto;
import org.servicestation.resources.dto.StationDto;
import org.servicestation.resources.exceptions.StationDoesNotExists;
import org.servicestation.resources.managers.IStationManager;
import org.servicestation.resources.mappers.IObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.stream.Collectors;

public class StationManagerImpl implements IStationManager{

    @Autowired
    private IStationDao stationDao;

    @Autowired
    private IObjectMapper mapper;

    @Override
    public StationDto createNewStation(StationDto newStation) {
        return mapper.mapServerObjectToDto(stationDao.createStation(mapper.mapDtoToServerObject(newStation)));
    }

    @Override
    public void deleteStation(Integer stationId) throws StationDoesNotExists {
        try{
            stationDao.getStationById(stationId);
            stationDao.deleteStation(stationId);
        }catch(EmptyResultDataAccessException e) {
            throw new StationDoesNotExists("Station with id " +  stationId + " does not exists");
        }
    }

    @Override
    public List<StationDto> getStations() {
        return stationDao.getAllStations().stream()
                .map(station -> mapper.<StationDto, Station>mapServerObjectToDto(station))
                .collect(Collectors.toList());
    }

    @Override
    public MapStationDto getMapStations() {
        MapStationDto mapStationDto = new MapStationDto();
        mapStationDto.features = stationDao.getAllStations().stream()
                .map(station -> new MapStationDto.StationFeature(station.id, station.latitude, station.longitude))
                .collect(Collectors.toList());

        return mapStationDto;
    }

    @Override
    public StationDto changeStation(Integer stationId, StationDto newStation) throws StationDoesNotExists {
        try{
            stationDao.getStationById(stationId);
            return mapper.mapServerObjectToDto(stationDao.changeStation(stationId, mapper.mapDtoToServerObject(newStation)));
        }catch(EmptyResultDataAccessException e) {
            throw new StationDoesNotExists("Station with id " +  stationId + " does not exists");
        }
    }
}
