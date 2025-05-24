package com.hse.analysis.kafka;

import com.hse.analysis.dto.FileUploadedEvent;
import com.hse.analysis.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class KafkaAnalysisListener {

    private final AnalysisService analysis;

    @KafkaListener(topics = "file-uploaded", groupId = "analysis")
    public void on(FileUploadedEvent evt) throws IOException {
        analysis.start(evt.fileId(), 30);
    }
}

