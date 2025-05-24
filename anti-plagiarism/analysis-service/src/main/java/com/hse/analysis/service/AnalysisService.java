package com.hse.analysis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hse.analysis.dto.AnalysisResponseDto;
import com.hse.analysis.model.AnalysisResult;
import com.hse.analysis.repository.AnalysisRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AnalysisService {

    private final AnalysisRepository repo;
    private final PdfExtractService  pdf;
    private final WordCloudService   wc;
    private final WebClient web = WebClient.builder()
            .exchangeStrategies(ExchangeStrategies.builder()
                    .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                    .build())
            .build();

    @Value("${storage.url}") private String storageUrl;
    @Value("${storage.internal-url:file-storage:8081}") private String storageInternal;

    public AnalysisResponseDto start(UUID fileId, int top) throws IOException {
        String url = storageUrl + "/storage/files/" + fileId + "/download";
        byte[] pdfBytes = web.get().uri(url).retrieve().bodyToMono(byte[].class).block();

        Map<String,Integer> freq = pdf.topWords(pdf.extractText(new ByteArrayInputStream(pdfBytes)), top);

        URI wcUrl = wc.buildWordCloud(freq);

        AnalysisResult saved = repo.save(new AnalysisResult(
                null, fileId,
                freq,
                wcUrl.toString(),
                Instant.now()
        ));

        return new AnalysisResponseDto(saved.getId(), fileId, freq, wcUrl);
    }

    public AnalysisResponseDto get(UUID id) throws JsonProcessingException {
        AnalysisResult ar = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("analysis "+id+" not found"));
        Map<String,Integer> map = ar.getTopWordsJson();
        return new AnalysisResponseDto(ar.getId(), ar.getFileId(), map, URI.create(ar.getWordCloudUrl()));
    }
}

