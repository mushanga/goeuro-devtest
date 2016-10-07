package com.goeuro.service;


import com.goeuro.dto.SuggestionResponseDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
public interface SuggestionService {

    List<SuggestionResponseDTO> search(@NotNull(message = "Search term can't be null")
                                       @Pattern(regexp = ".*\\S.*", message = "Search term can't be empty") String name);

}