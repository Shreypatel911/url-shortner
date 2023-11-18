package com.example.urlshortner.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlDto {
    private String url;
    private String expirationDate;
}
