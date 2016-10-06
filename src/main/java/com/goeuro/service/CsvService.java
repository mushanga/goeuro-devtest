package com.goeuro.service;


import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface CsvService {

    <T> String toCsv(@NotNull Class<T> targetClass, @NotNull List<T> objects, String... orderedHeaders);

}