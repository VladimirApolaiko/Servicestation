package org.servicestation.resources.dto;

import java.util.ArrayList;
import java.util.List;

public class StationDto {
    public final String type = "FeatureCollection";
    public List<StationFeature> features = new ArrayList<>();

    public static class StationFeature {
        public StationFeature(Integer stationId, Double latitude, Double longitude) {
            this.properties.stationId = stationId;
            this.geometry.coordinates.add(latitude);
            this.geometry.coordinates.add(longitude);
        }

        public final String type = "Feature";
        public StationProperty properties = new StationProperty();
        public StationGeometry geometry = new StationGeometry();
    }

    public static class StationProperty {
        public Integer stationId;
    }

    public static class StationGeometry {
        public final String type = "Point";
        public List<Double> coordinates = new ArrayList<>();
    }
}
