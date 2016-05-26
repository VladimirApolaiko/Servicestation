package org.servicestation.resources.dto;

import java.util.ArrayList;
import java.util.List;

public class StationDto {

    public StationDto(Integer stationId, Double latitude, Double longitude) {
        this.properties.stationId = stationId;
        this.geometry.coordinates.add(longitude);
        this.geometry.coordinates.add(latitude);
    }

    public final String type = "FeatureCollection";
    public List<StationFeature> features = new ArrayList<>();
    public StationProperty properties = new StationProperty();
    public StationGeometry geometry = new StationGeometry();

    public class StationFeature {
        public final String type = "Feature";

    }

    public class StationProperty {
        public Integer stationId;
    }

    public class StationGeometry {
        public final String type = "Point";
        List<Double> coordinates = new ArrayList<>();
    }
}
