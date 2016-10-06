package com.goeuro.service;


import com.goeuro.dto.SuggestionOutputDTO;
import com.goeuro.dto.SuggestionResponseDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface BeanConverterService {
	SuggestionOutputDTO suggestionResponseToOutput(@NotNull SuggestionResponseDTO suggestionResponseDTO);

}