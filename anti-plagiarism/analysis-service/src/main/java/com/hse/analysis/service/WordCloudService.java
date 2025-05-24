package com.hse.analysis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordCloudService {

    @Value("${quickchart.base-url:https://quickchart.io/wordcloud}")
    private String quickChart;

    public URI buildWordCloud(Map<String,Integer> words) {
        String joined = words.entrySet().stream()
                .map(e -> (e.getValue() + ":" + URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8)))
                .collect(Collectors.joining(","));
        String url = String.format("%s?format=png&width=600&height=400&fontScale=15&text=%s&weighted=true",
                quickChart, joined);
        return URI.create(url);
    }
}

