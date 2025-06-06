package com.hse.analysis.dto;

import java.util.UUID;

public record StartAnalysisRequest(UUID fileId, int top) { }

