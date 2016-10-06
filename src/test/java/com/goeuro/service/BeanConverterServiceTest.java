package com.goeuro.service;

import com.goeuro.TestBase;
import com.goeuro.dto.SuggestionOutputDTO;
import com.goeuro.dto.SuggestionResponseDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BeanConverterServiceTest extends TestBase{
    @Autowired
    private BeanConverterService beanConverter;
    @Test
    public void testSuggestionResponseToOutputTest() throws Exception {
        SuggestionResponseDTO suggestionResponse = createSuggestionResponse(450419L, "Tegelen", "location", 51.34417, 6.13611);
        SuggestionOutputDTO suggestionOutput = beanConverter.suggestionResponseToOutput(suggestionResponse);

        Assert.assertEquals((Long)450419L,  suggestionOutput.getId());
        Assert.assertEquals("Tegelen",  suggestionOutput.getName());
        Assert.assertEquals("location",  suggestionOutput.getType());
        Assert.assertEquals((Double) 51.34417,  suggestionOutput.getLatitude());
        Assert.assertEquals((Double) 6.13611,  suggestionOutput.getLongitude());
    }
    @Test(expected = ConstraintViolationException.class)
    public void testNullArg(){
        beanConverter.suggestionResponseToOutput(null);
    }

}