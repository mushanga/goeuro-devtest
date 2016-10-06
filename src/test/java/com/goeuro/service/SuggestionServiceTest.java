package com.goeuro.service;

import com.goeuro.TestBase;
import com.goeuro.dto.SuggestionResponseDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SuggestionServiceTest extends TestBase{

    private MockRestServiceServer mockServer;


    @Autowired
    private RestTemplate suggestionRestTemplate;

    @Autowired
    private SuggestionService suggestionService;

    @Value("${suggestionws.url}")
    private String suggestionWSUrl;

    @Before
    public void setupMockServer() {
        mockServer = MockRestServiceServer.createServer(suggestionRestTemplate);
    }

    @Test
    public void testSuggestionService() throws IOException {
        mockServer.expect(requestTo(suggestionWSUrl.replace("{term}", TERM)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getMockServiceResponseContent(), MediaType.APPLICATION_JSON));

        List<SuggestionResponseDTO> suggestions = suggestionService.search(TERM);

        Assert.assertEquals((Long) 450419L, suggestions.get(0).getId());
        Assert.assertEquals("Tegelen", suggestions.get(0).getName());
        Assert.assertEquals("location", suggestions.get(0).getType());
        Assert.assertEquals((Double)51.34417, suggestions.get(0).getGeoPosition().getLatitude());
        Assert.assertEquals((Double)6.13611, suggestions.get(0).getGeoPosition().getLongitude());

        Assert.assertEquals((Long) 314826L, suggestions.get(1).getId());
        Assert.assertEquals("Berlin Tegel", suggestions.get(1).getName());
        Assert.assertEquals("airport", suggestions.get(1).getType());
        Assert.assertEquals((Double)52.5548, suggestions.get(1).getGeoPosition().getLatitude());
        Assert.assertEquals((Double)13.28903, suggestions.get(1).getGeoPosition().getLongitude());

        mockServer.verify();
    }
    @Test(expected = ConstraintViolationException.class)
    public void testNullTerm(){
        suggestionService.search(null);
    }
    @Test(expected = ConstraintViolationException.class)
    public void testBlankTerm(){
        suggestionService.search("    ");
    }

}