package com.goeuro;

import com.goeuro.dto.GeoPositionDTO;
import com.goeuro.dto.SuggestionOutputDTO;
import com.goeuro.dto.SuggestionResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

public abstract class TestBase {

    private final Logger log = LoggerFactory.getLogger(TestBase.class);

    protected static final String TERM = "tegel";

    @Autowired
    private ResourceLoader resourceLoader;

    protected String getMockServiceResponseContent() {
        return readResource("classpath:tegel.json");
    }

    protected String getExpectedOutputContent() {
        return readResource("classpath:tegel.csv");
    }

    protected SuggestionResponseDTO createSuggestionResponse(Long id, String name, String type, Double latitude, Double longitude) {
        SuggestionResponseDTO suggestionResponseDTO = new SuggestionResponseDTO();
        suggestionResponseDTO.setId(id);
        suggestionResponseDTO.setName(name);
        suggestionResponseDTO.setType(type);
        suggestionResponseDTO.setGeoPosition(new GeoPositionDTO());
        suggestionResponseDTO.getGeoPosition().setLatitude(latitude);
        suggestionResponseDTO.getGeoPosition().setLongitude(longitude);
        return suggestionResponseDTO;
    }

    protected SuggestionOutputDTO createSuggestionOutput(Long id, String name, String type, Double latitude, Double longitude) {
        SuggestionOutputDTO suggestionOutputDTO = new SuggestionOutputDTO();
        suggestionOutputDTO.setId(id);
        suggestionOutputDTO.setName(name);
        suggestionOutputDTO.setType(type);
        suggestionOutputDTO.setLatitude(latitude);
        suggestionOutputDTO.setLongitude(longitude);
        return suggestionOutputDTO;
    }

    private String readResource(String uri) {
        try {
            return Files.readAllLines(
                    resourceLoader
                            .getResource(uri)
                            .getFile()
                            .toPath())
                    .stream()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            log.error("Unexpected error occurred while reading the resource");
            throw new RuntimeException(e);
        }
    }
}
