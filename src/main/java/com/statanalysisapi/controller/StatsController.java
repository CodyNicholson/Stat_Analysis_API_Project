package com.statanalysisapi.controller;

import com.statanalysisapi.dto.BasicStatsRequestDto;
import com.statanalysisapi.dto.BasicStatsResponseDto;
import com.statanalysisapi.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class StatsController {
    @Autowired
    private StatsService statsService;

    @PostMapping(value = "/stats")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicStatsResponseDto> getBasicStats(
            @RequestBody BasicStatsRequestDto basicStatsRequestDto
            ) {
        log.info("Input number strings: " + basicStatsRequestDto.toString());

        BasicStatsResponseDto basicStatsResponseDto = statsService.getBasicStats(basicStatsRequestDto);

        return new ResponseEntity<>(basicStatsResponseDto, HttpStatus.OK);
    }
}
