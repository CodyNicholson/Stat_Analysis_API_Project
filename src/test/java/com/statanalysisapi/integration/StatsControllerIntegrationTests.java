package com.statanalysisapi.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statanalysisapi.constant.Constants;
import com.statanalysisapi.dto.BasicStatsRequestDto;
import com.statanalysisapi.dto.BasicStatsResponseDto;
import com.statanalysisapi.error.ErrorResponseModel;
import com.statanalysisapi.util.SpringCommandLineProfileResolver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(resolver = SpringCommandLineProfileResolver.class)
public class StatsControllerIntegrationTests {
    @Autowired
    TestRestTemplate testRestTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void stats_validEvenNumInput_200() {
        List<String> inputInts = Arrays.asList("4", "2", "1", "3");
        BasicStatsRequestDto basicStatsRequestDto = new BasicStatsRequestDto(inputInts);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity inputRequest = new HttpEntity(basicStatsRequestDto, httpHeaders);
        ResponseEntity<BasicStatsResponseDto> response = testRestTemplate.exchange("/stats", HttpMethod.POST, inputRequest, BasicStatsResponseDto.class);

        assertNotNull(response.getBody());
        assertEquals("4", response.getBody().getCount());
        assertEquals("1", response.getBody().getMinimum());
        assertEquals("4", response.getBody().getMaximum());
        assertEquals("2.50", response.getBody().getMedian());
        assertEquals("2.50", response.getBody().getMean());
        assertEquals("0", response.getBody().getErrors());
    }

    @Test
    void stats_validOddNumInput_200() {
        List<String> inputInts = Arrays.asList("10", "2", "3", "4", "5");
        BasicStatsRequestDto basicStatsRequestDto = new BasicStatsRequestDto(inputInts);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity inputRequest = new HttpEntity(basicStatsRequestDto, httpHeaders);
        ResponseEntity<BasicStatsResponseDto> response = testRestTemplate.exchange("/stats", HttpMethod.POST, inputRequest, BasicStatsResponseDto.class);

        assertNotNull(response.getBody());
        assertEquals("5", response.getBody().getCount());
        assertEquals("2", response.getBody().getMinimum());
        assertEquals("10", response.getBody().getMaximum());
        assertEquals("4.00", response.getBody().getMedian());
        assertEquals("4.80", response.getBody().getMean());
        assertEquals("0", response.getBody().getErrors());
    }

    @Test
    void stats_onlyOneValidInput_200() {
        List<String> inputInts = Arrays.asList("4");
        BasicStatsRequestDto basicStatsRequestDto = new BasicStatsRequestDto(inputInts);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity inputRequest = new HttpEntity(basicStatsRequestDto, httpHeaders);
        ResponseEntity<BasicStatsResponseDto> response = testRestTemplate.exchange("/stats", HttpMethod.POST, inputRequest, BasicStatsResponseDto.class);

        assertNotNull(response.getBody());
        assertEquals("1", response.getBody().getCount());
        assertEquals("4", response.getBody().getMinimum());
        assertEquals("4", response.getBody().getMaximum());
        assertEquals("4.00", response.getBody().getMedian());
        assertEquals("4.00", response.getBody().getMean());
        assertEquals("0", response.getBody().getErrors());
    }

    @Test
    void stats_validAndInvalidInput_200() {
        List<String> inputInts = Arrays.asList("string", "2", "2", "2", "2", "3", "-123", "0");
        BasicStatsRequestDto basicStatsRequestDto = new BasicStatsRequestDto(inputInts);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity inputRequest = new HttpEntity(basicStatsRequestDto, httpHeaders);
        ResponseEntity<BasicStatsResponseDto> response = testRestTemplate.exchange("/stats", HttpMethod.POST, inputRequest, BasicStatsResponseDto.class);

        assertNotNull(response.getBody());
        assertEquals("5", response.getBody().getCount());
        assertEquals("2", response.getBody().getMinimum());
        assertEquals("3", response.getBody().getMaximum());
        assertEquals("2.00", response.getBody().getMedian());
        assertEquals("2.20", response.getBody().getMean());
        assertEquals("3", response.getBody().getErrors());
    }

    @Test
    void stats_noValidInput_400BadRequest() throws JsonProcessingException {
        List<String> inputInts = Arrays.asList("string", "-123", "0");
        BasicStatsRequestDto basicStatsRequestDto = new BasicStatsRequestDto(inputInts);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity inputRequest = new HttpEntity(basicStatsRequestDto, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange("/stats", HttpMethod.POST, inputRequest, String.class);
        ErrorResponseModel errorResponse = this.objectMapper.readValue(response.getBody(), ErrorResponseModel.class);

        assertNotNull(response);
        assertNotNull(errorResponse.getDate());
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getStatusCode());
        assertEquals(Constants.REST_BAD_REQUEST, errorResponse.getRestErrorMessage());
        assertEquals(Constants.NO_VALID_INTEGERS_PROVIDED, errorResponse.getDetailedErrorMessage());
    }

    @Test
    void stats_invalidJson_400BadRequest() throws JsonProcessingException {
        String inputJson = "}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity inputRequest = new HttpEntity(inputJson, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange("/stats", HttpMethod.POST, inputRequest, String.class);
        ErrorResponseModel errorResponse = this.objectMapper.readValue(response.getBody(), ErrorResponseModel.class);

        assertNotNull(response);
        assertNotNull(errorResponse.getDate());
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getStatusCode());
        assertEquals(Constants.REST_BAD_REQUEST, errorResponse.getRestErrorMessage());
        assertEquals(Constants.INVALID_JSON, errorResponse.getDetailedErrorMessage());
    }
}
