package com.statanalysisapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicStatsResponseDto {
    private String count;
    private String minimum;
    private String maximum;
    private String mean;
    private String median;
    private String errors;
}
