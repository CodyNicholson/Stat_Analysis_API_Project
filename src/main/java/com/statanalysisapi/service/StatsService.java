package com.statanalysisapi.service;

import com.statanalysisapi.dto.BasicStatsRequestDto;
import com.statanalysisapi.dto.BasicStatsResponseDto;

public interface StatsService {
    BasicStatsResponseDto getBasicStats(BasicStatsRequestDto basicStatsRequestDto);
}
