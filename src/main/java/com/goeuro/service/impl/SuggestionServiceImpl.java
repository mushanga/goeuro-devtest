package com.goeuro.service.impl;

import com.goeuro.dto.SuggestionResponseDTO;
import com.goeuro.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class SuggestionServiceImpl implements SuggestionService {
    @Value("${suggestionws.url}")
    private String suggestionWSUrl;

    @Autowired
    private RestTemplate suggestionRestTemplate;

    @Override
    public List<SuggestionResponseDTO> search(String term) {

        return Arrays.asList(
                suggestionRestTemplate
                        .getForEntity(suggestionWSUrl, SuggestionResponseDTO[].class, term)
                        .getBody());
    }

    @Bean
    public RestTemplate getSuggestionRestTemplate() {
        return new RestTemplate();
    }
}