package com.statanalysisapi.service.impl;

import com.statanalysisapi.constant.Constants;
import com.statanalysisapi.dto.BasicStatsRequestDto;
import com.statanalysisapi.dto.BasicStatsResponseDto;
import com.statanalysisapi.error.restCustomExceptions.BadRequestException;
import com.statanalysisapi.service.StatsService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public StatsServiceImpl() {
        df.setMinimumFractionDigits(2);
    }

    @Override
    public BasicStatsResponseDto getBasicStats(BasicStatsRequestDto basicStatsRequestDto) {
        List<Integer> validInts = validateInput(basicStatsRequestDto.getInputIntStrings());
        int requestSize = basicStatsRequestDto.getInputIntStrings().size();

        String count = validInts.size() + "";
        String minInt = validInts.stream().min(Integer::compare).get() + "";
        String maxInt = validInts.stream().max(Integer::compare).get() + "";
        String mean = df.format(getMean(validInts));
        String median = df.format(getMedian(validInts));
        String errors = requestSize - validInts.size() + "";

        return new BasicStatsResponseDto(count, minInt, maxInt, mean, median, errors);
    }

    private double getMean(List<Integer> inputInts) {
        double sumInts = inputInts.stream().mapToDouble(a -> a).sum();

        return sumInts / inputInts.size();
    }

    private double getMedian(List<Integer> inputInts) {
        double median;

        if (inputInts.size() == 1) {
            return inputInts.get(0);
        }

        int indexOfFirstMedianNumber = inputInts.size()/2;

        if (inputInts.size() % 2 == 0) {
            int indexOfSecondMedianNumber = inputInts.size()/2 - 1;
            median = (double) (inputInts.get(indexOfFirstMedianNumber) + inputInts.get(indexOfSecondMedianNumber)) / 2;
        } else {
            median = inputInts.get(indexOfFirstMedianNumber);
        }

        return median;
    }

    private List<Integer> validateInput(List<String> inputNumberStrings) {
        if (inputNumberStrings.size() == 0) {
            throw new BadRequestException(Constants.NO_VALUES_PROVIDED, null);
        }

        List<Integer> integerList = new ArrayList<>();

        for (String intStr: inputNumberStrings) {
            try {
                int parsedInt = Integer.parseInt(intStr);

                if (parsedInt > 0) {
                    integerList.add(parsedInt);
                } else {
                    throw new NumberFormatException(Constants.ONLY_POSITIVE_INTEGERS_ALLOWED);
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.NA);
            }
        }

        if (integerList.isEmpty()) {
            throw new BadRequestException(Constants.NO_VALID_INTEGERS_PROVIDED, null);
        }

        Collections.sort(integerList);

        return integerList;
    }
}
