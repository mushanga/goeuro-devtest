package com.goeuro.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.goeuro.service.CsvService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsvServiceImpl implements CsvService {

    @Override
    public <T> String toCsv(Class<T> targetClass, List<T> objects, String... orderedHeaders) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(targetClass)
                .withHeader()
                .sortedBy(orderedHeaders);
        try {
            return mapper.writer(schema)
                    .writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unexpected error occurred while generating the CSV content", e);
        }
    }
}