package com.goeuro.service;

import com.goeuro.TestBase;
import org.junit.After;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SuggestionExportServiceTest extends TestBase {

	private static final String TEST_RESULTS_FILE_NAME = "test_results.csv";
	private MockRestServiceServer mockServer;

	@Autowired
	private RestTemplate suggestionRestTemplate;

	@Autowired
	private SuggestionExportService suggestionExportService;

	private boolean fileAlreadyExisted;

	@Value("${suggestionws.url}")
	private String suggestionWSUrl;

	@Before
	public void checkIfTestResultsExist() throws Exception {
		mockServer = MockRestServiceServer.createServer(suggestionRestTemplate);

		fileAlreadyExisted = Files.exists(Paths.get(TEST_RESULTS_FILE_NAME));
		Assert.assertFalse(
				String.format("%s already exists. It must be deleted before running tests.", TEST_RESULTS_FILE_NAME),
				fileAlreadyExisted);
	}
	@After
	public void deleteTestResults() throws Exception {
		if(!fileAlreadyExisted){
			Files.deleteIfExists(Paths.get(TEST_RESULTS_FILE_NAME));
		}
	}
	@Test
	public void testSearchAndExport() throws Exception {
		mockServerExpectSuccess(TERM);

		suggestionExportService.searchAndExport(TERM, TEST_RESULTS_FILE_NAME);
		Assert.assertEquals(getExpectedOutputContent(), getProcessResultCsvContent());

		mockServer.verify();
	}

	@Test(expected = ConstraintViolationException.class)
	public void testNullTerm(){
		suggestionExportService.searchAndExport(null, "abc");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testBlankTerm(){
		suggestionExportService.searchAndExport("  ", null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testNullOutputFile(){
		suggestionExportService.searchAndExport("abc", null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testEmptyOutputFile(){
		suggestionExportService.searchAndExport("abc", "  ");
	}

	private String getProcessResultCsvContent() throws IOException {
		return Files.readAllLines(Paths.get(TEST_RESULTS_FILE_NAME))
				.stream()
				.collect(Collectors.joining("\n"));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testInvalidOutputFile(){
		suggestionExportService.searchAndExport(TERM, "asd$.csv");
	}

	private void mockServerExpectSuccess(String term) {
		mockServer.expect(requestTo(suggestionWSUrl.replace("{term}", term)))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(getMockServiceResponseContent(), MediaType.APPLICATION_JSON));
	}
}
