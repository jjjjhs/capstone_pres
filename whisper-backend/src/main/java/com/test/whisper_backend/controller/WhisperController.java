package com.test.whisper_backend.controller;

import com.test.whisper_backend.MultipartInputStreamFileResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;

import java.io.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class WhisperController {

    private static final String OPENAI_API_KEY    = "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";  // your key
    private static final String WHISPER_API_URL   = "https://api.openai.com/v1/audio/transcriptions";

    // 최대 청크 크기 (MB)
    private static final double CHUNK_SIZE_MB     = 20.0;
    // WAV 16kHz 기준 약 13.3MB / 7분 ≒ 1.9MB/분
    private static final double AVG_MB_PER_MINUTE = 13.3 / 7.0;
    // 청크 간 경계 오버랩 (초)
    private static final double OVERLAP_SEC       = 2.0;

    @PostMapping("/whisper-multi")
    public ResponseEntity<Map<String,String>> recognizeMulti(@RequestParam("audio") MultipartFile audioFile) {
        Map<String,String> result = new HashMap<>();
        try {
            // 1) 임시 WAV 파일로 저장
            File tempWav = File.createTempFile("upload", ".wav");
            audioFile.transferTo(tempWav);

            // 2) 실제 재생 길이 구하기
            AudioInputStream ais    = AudioSystem.getAudioInputStream(tempWav);
            AudioFormat       fmt    = ais.getFormat();
            double            totalSec = ais.getFrameLength() / fmt.getFrameRate();
            ais.close();

            // 3) 청크 길이 계산 (초 단위)
            double chunkSec = CHUNK_SIZE_MB / AVG_MB_PER_MINUTE * 60.0;
            int    numChunks = (int) Math.ceil(totalSec / chunkSec);

            System.out.printf("📦 파일 크기: %.2f MB, 재생 길이: %.2f 초 → 청크(%,.2f초) 개수: %d%n",
                              audioFile.getSize()/(1024.0*1024.0),
                              totalSec,
                              chunkSec,
                              numChunks);

            List<String> texts = new ArrayList<>();

            // 4) 각 청크별 분할 & Whisper 호출
            for (int i = 0; i < numChunks; i++) {
                double start = i * chunkSec - OVERLAP_SEC;
                if (start < 0) start = 0;
                double dur = Math.min(chunkSec + OVERLAP_SEC*2, totalSec - start);
                if (dur <= 0) break;

                // 4-1) FFmpeg로 분할
                String chunkPath = tempWav.getParent() + "/chunk_" + i + ".wav";
                String ffmpeg    = "/opt/homebrew/bin/ffmpeg";  // macOS 예시
                String cmd = String.format(
                    "%s -y -ss %.2f -i %s -t %.2f %s",
                    ffmpeg, start, tempWav.getAbsolutePath(), dur, chunkPath
                );
                System.out.println("🎬 [FFmpeg 실행] " + cmd);
                Process p = Runtime.getRuntime().exec(cmd);
                int code = p.waitFor();
                System.out.println("🎬 [FFmpeg 종료] Exit code: " + code);

                File chunkFile = new File(chunkPath);
                System.out.printf("✅ [분할 완료] chunk_%d.wav (%.2f MB, %.2f초)%n",
                                  i,
                                  chunkFile.length()/(1024.0*1024.0),
                                  dur
                );

                // 4-2) Whisper API 호출
                String text = callWhisper(chunkFile);
                System.out.printf("📡 [Whisper 응답] chunk_%d → %s%n", i, text);
                texts.add(text);

                // 4-3) 임시 청크 삭제
                if (!chunkFile.delete()) {
                    System.err.println("⚠️ [삭제 실패] " + chunkFile.getAbsolutePath());
                }
            }

            // 5) 전체 결과 합치기 & 응답
            String joined = String.join("\n", texts);
            result.put("result", joined);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", "❌ 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    /** Whisper API 호출 */
    private String callWhisper(File file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(OPENAI_API_KEY);

        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(
            new FileInputStream(file), file.getName()
        ));
        body.add("model", "whisper-1");

        HttpEntity<MultiValueMap<String,Object>> req = new HttpEntity<>(body, headers);
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Map> resp = rest.postForEntity(WHISPER_API_URL, req, Map.class);

        @SuppressWarnings("unchecked")
        String text = (String) resp.getBody().get("text");
        return (text != null ? text : "");
    }
}
