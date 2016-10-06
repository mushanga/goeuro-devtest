package com.goeuro.service.impl;

import com.goeuro.dto.SuggestionOutputDTO;
import com.goeuro.dto.SuggestionResponseDTO;
import com.goeuro.service.BeanConverterService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BeanConverterServiceImpl implements BeanConverterService {
    public SuggestionOutputDTO suggestionResponseToOutput(SuggestionResponseDTO suggestionResponseDTO) {
        SuggestionOutputDTO suggestionOutputDTO = new SuggestionOutputDTO();
        suggestionOutputDTO.setId(suggestionResponseDTO.getId());
        suggestionOutputDTO.setName(suggestionResponseDTO.getName());
        suggestionOutputDTO.setType(suggestionResponseDTO.getType());
        if (Objects.nonNull(suggestionResponseDTO.getGeoPosition())) {
            suggestionOutputDTO.setLatitude(suggestionResponseDTO.getGeoPosition().getLatitude());
            suggestionOutputDTO.setLongitude(suggestionResponseDTO.getGeoPosition().getLongitude());
        }
        return suggestionOutputDTO;
    }
}
