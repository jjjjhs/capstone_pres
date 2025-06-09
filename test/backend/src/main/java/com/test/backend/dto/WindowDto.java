package com.test.backend.dto;

import java.util.Map;

public class WindowDto {
    private double startSec;
    private double endSec;
    private String transcript;             // Whisper로부터 받은 텍스트
    private Map<String, Integer> fillers;  // 추임새 카운트 맵
    private int spm;
    private int spmScore;

    public WindowDto(
            double startSec,
            double endSec,
            String transcript,
            Map<String, Integer> fillers,
            int spm,
            int spmScore
    ) {
        this.startSec   = startSec;
        this.endSec     = endSec;
        this.transcript = transcript;
        this.fillers    = fillers;
        this.spm        = spm;
        this.spmScore   = spmScore;
    }
    // Getter/Setter (or Lombok @Data) 추가
    public double getStartSec() { return startSec; }
    public void setStartSec(double startSec) { this.startSec = startSec; }

    public double getEndSec() { return endSec; }
    public void setEndSec(double endSec) { this.endSec = endSec; }

    public String getTranscript() { return transcript; }
    public void setTranscript(String transcript) { this.transcript = transcript; }

    public Map<String, Integer> getFillers() { return fillers; }
    public void setFillers(Map<String, Integer> fillers) { this.fillers = fillers; }

    public int getSpm() { return spm; }
    public void setSpm(int spm) { this.spm = spm; }

    public int getSpmScore() { return spmScore; }
    public void setSpmScore(int spmScore) { this.spmScore = spmScore; }
}
