package com.example.kakaologin.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KakaoIdDto {
    @JsonProperty("id")
    Long id;

    @JsonProperty("connected_at")
    LocalDateTime connectedAt;
}
