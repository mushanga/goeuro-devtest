package com.goeuro.service;


import com.goeuro.dto.SuggestionResponseDTO;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface SuggestionService {

	List<SuggestionResponseDTO> search(@NotBlank String name);

}