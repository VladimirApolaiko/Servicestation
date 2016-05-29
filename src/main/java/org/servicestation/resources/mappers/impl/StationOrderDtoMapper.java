package org.servicestation.resources.mappers.impl;

import org.servicestation.model.StationOrder;
import org.servicestation.resources.dto.StationOrderDto;
import org.servicestation.resources.mappers.IDtoMapper;
import org.servicestation.resources.utils.Utils;

public class StationOrderDtoMapper implements IDtoMapper<StationOrderDto, StationOrder> {

    @Override
    public StationOrder mapDtoToServerObject(StationOrderDto dto) {
        StationOrder stationOrder = new StationOrder();

        stationOrder.orderId = dto.orderId;
        stationOrder.stationId = dto.stationId;
        stationOrder.localDateTime = Utils.getLocalDateTime(dto.timestamp);

        return stationOrder;
    }

    @Override
    public StationOrderDto mapServerObjectToDto(StationOrder serverObj) {
        StationOrderDto dto = new StationOrderDto();

        dto.id = serverObj.id;
        dto.orderId = serverObj.orderId;
        dto.stationId = serverObj.stationId;
        dto.timestamp = Utils.getStringLocalDateTimeFormat(serverObj.localDateTime);

        return dto;
    }

    @Override
    public Class getDtoType() {
        return StationOrderDto.class;
    }

    @Override
    public Class getServerObjectType() {
        return StationOrder.class;
    }
}
