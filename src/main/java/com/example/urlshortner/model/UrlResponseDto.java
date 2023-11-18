package com.example.urlshortner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponseDto {
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime expirationDate;
}
