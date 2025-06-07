package com.intellectus.backend.dto;

public class TranscriptionResponseDto {
    private Integer id;
    private String audioPath;
    private String transcription;

    public TranscriptionResponseDto(Integer id, String audioPath, String transcription) {
        this.id = id;
        this.audioPath = audioPath;
        this.transcription = transcription;
    }
    public Integer getId() {
        return id;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }
}
