package com.goeuro.service.impl;

import com.goeuro.dto.SuggestionOutputDTO;
import com.goeuro.service.BeanConverterService;
import com.goeuro.service.CsvService;
import com.goeuro.service.SuggestionExportService;
import com.goeuro.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionExportServiceImpl implements SuggestionExportService {

    public static final String[] ORDERED_HEADERS = {"_id", "name", "type", "latitude", "longitude"};

    @Autowired
    private SuggestionService suggestionService;
    @Autowired
    private CsvService csvService;
    @Autowired
    private BeanConverterService beanConverter;

    public void searchAndExport(String term, String outputFilename) {

        List<SuggestionOutputDTO> suggestionOutputs = suggestionService.search(term)
                .stream()
                .map(suggestionResponse -> beanConverter.suggestionResponseToOutput(suggestionResponse))
                .collect(Collectors.toList());

        String csvContent = csvService.toCsv(
                SuggestionOutputDTO.class,
                suggestionOutputs,
                ORDERED_HEADERS);

        try {
            Files.write(Paths.get(outputFilename), csvContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error occurred while writing the CSV content into file", e);
        }
    }
}