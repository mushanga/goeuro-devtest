package com.goeuro.service;


import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Validated
public interface SuggestionExportService {

    void searchAndExport(@Pattern(regexp = ".*\\S.*", message = "Search term can't be empty") String term,
                         @NotNull(message = "Output file name can't be null")
                         @Pattern(regexp = "[a-zA-Z0-9\\.\\-_]+",
                                 message = "Output file name can contain only " +
                                         "alphanumeric characters, '-', '_', and '.' ") String outputFilename);

}