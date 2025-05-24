package com.hse.analysis.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PdfExtractService {

    public String extractText(InputStream pdf) throws IOException {
        try (PDDocument doc = Loader.loadPDF(new RandomAccessReadBuffer(pdf))) {
            return new PDFTextStripper().getText(doc);
        }
    }

    public Map<String,Integer> topWords(String text, int limit) {
        Pattern splitter = Pattern.compile("\\P{L}+");
        return Arrays.stream(splitter.split(text.toLowerCase()))
                .filter(w -> w.length() >= 3)
                .collect(Collectors.groupingBy(w -> w, Collectors.summingInt(w -> 1)))
                .entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a,b)->a, HashMap::new));
    }
}

