package com.goeuro.service;

import com.goeuro.TestBase;
import com.goeuro.dto.SuggestionOutputDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CsvServiceTest extends TestBase {

    @Autowired
    private CsvService csvService;

    @Test
    public void testBeanToCsv() throws Exception {
        List<SuggestionOutputDTO> outputs = new ArrayList<>();
        outputs.add(createSuggestionOutput(450419L, "Tegelen", "location", 51.34417, 6.13611));
        outputs.add(createSuggestionOutput(314826L, "Berlin Tegel", "airport", 52.5548, 13.28903));

        String csvContent = csvService.toCsv(SuggestionOutputDTO.class, outputs, "_id", "name", "type", "latitude", "longitude");
        String expectedCsvContent = "_id,name,type,latitude,longitude\n" +
                "450419,Tegelen,location,51.34417,6.13611\n" +
                "314826,\"Berlin Tegel\",airport,52.5548,13.28903\n";
        Assert.assertEquals(expectedCsvContent, csvContent);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNullClass() {
        csvService.toCsv(null, Collections.emptyList());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNullList() {
        csvService.toCsv(SuggestionOutputDTO.class, null);
    }

}