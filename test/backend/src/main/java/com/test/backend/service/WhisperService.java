package com.test.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

@Service
public class WhisperService {
    private static final Logger log = LoggerFactory.getLogger(WhisperService.class);
    private static final String OPENAI_API_KEY  = "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";  // API 키
    private static final String WHISPER_API_URL = "https://api.openai.com/v1/audio/transcriptions";

    public String transcribe(File wavFile) throws Exception {
        log.info("      ▶ Preparing Whisper request for file: {}", wavFile.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(OPENAI_API_KEY);

        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(wavFile));
        body.add("model", "whisper-1");

        HttpEntity<MultiValueMap<String,Object>> req = new HttpEntity<>(body, headers);
        log.info("      ▶ Sending Whisper API request...");
        RestTemplate rest = new RestTemplate();
        @SuppressWarnings("unchecked")
        Map<String,Object> resp = rest.postForObject(WHISPER_API_URL, req, Map.class);

        String text = (String) resp.getOrDefault("text", "");
        log.info("      ▶ Whisper API responded, text length = {}", text.length());
        return text;
    }
}
