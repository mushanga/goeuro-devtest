package com.goeuro;

import com.goeuro.service.SuggestionExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Profile("!test")
public class ApplicationCommandLineRunner implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SuggestionExportService suggestionExportService;

    @Value("${output.filename}")
    private String outputFilename;

    @Override
    public void run(String... args) throws Exception {

        if (args == null || args.length == 0) throw new IllegalArgumentException("Command line argument must exist");

        String term = Arrays.asList(args)
                .stream()
                .collect(Collectors.joining(" "))
                .trim();
        try {
            suggestionExportService.searchAndExport(term, outputFilename);
        } catch (ConstraintViolationException e) {
            throw new IllegalArgumentException(
                    "Argument(s) invalid: " + e.getConstraintViolations()
                            .stream()
                            .map(cv -> "[" + cv.getMessage() + "]")
                            .collect(Collectors.joining(", ")),
                    e);
        }

    }


}