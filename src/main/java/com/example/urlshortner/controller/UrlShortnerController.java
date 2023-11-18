package com.example.urlshortner.controller;

import com.example.urlshortner.model.Url;
import com.example.urlshortner.model.UrlDto;
import com.example.urlshortner.model.UrlErrorResponseDto;
import com.example.urlshortner.model.UrlResponseDto;
import com.example.urlshortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UrlShortnerController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateShortLink(@RequestBody UrlDto url){
        Url urlToRet = urlService.generateShortLink(url);

        if(urlToRet != null){
            UrlResponseDto urlResponseDto = new UrlResponseDto();
            urlResponseDto.setOriginalUrl(urlToRet.getOriginalLink());
            urlResponseDto.setShortUrl(urlToRet.getShortLink());
            urlResponseDto.setExpirationDate(urlToRet.getExpirationDate());

            return new ResponseEntity<>(urlResponseDto, HttpStatus.OK);
        }

        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        urlErrorResponseDto.setError("Error!!");
        urlErrorResponseDto.setStatus("400");

        return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
    }
}
