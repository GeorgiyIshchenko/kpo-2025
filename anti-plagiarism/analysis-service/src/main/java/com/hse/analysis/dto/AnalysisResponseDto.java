package com.hse.analysis.dto;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

public record AnalysisResponseDto(
        UUID id,
        UUID fileId,
        Map<String, Integer> topWords,
        URI wordCloudUrl
) { }
