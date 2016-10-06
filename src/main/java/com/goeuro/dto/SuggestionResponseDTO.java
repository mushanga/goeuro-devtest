package com.goeuro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuggestionResponseDTO {
    @JsonProperty("_id")
    private Long id;

    private String name;

    private String type;

    @JsonProperty("geo_position")
    private GeoPositionDTO geoPosition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeoPositionDTO getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPositionDTO geoPosition) {
        this.geoPosition = geoPosition;
    }
}