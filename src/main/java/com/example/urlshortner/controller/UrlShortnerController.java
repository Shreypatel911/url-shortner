package com.example.urlshortner.controller;

import com.example.urlshortner.model.Url;
import com.example.urlshortner.model.UrlDto;
import com.example.urlshortner.model.UrlErrorResponseDto;
import com.example.urlshortner.model.UrlResponseDto;
import com.example.urlshortner.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.time.LocalDateTime;

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

    @GetMapping("/{shortLink}")
    public ResponseEntity<?> openOriginalLink(@PathVariable String shortLink, HttpServletResponse response) throws IOException {
        if(StringUtils.isEmpty(shortLink)){
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setStatus("400");
            urlErrorResponseDto.setError("Invalid Url");

            return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
        }

        Url url = urlService.getEncodedUrl(shortLink);
        if(url == null){
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setStatus("400");
            urlErrorResponseDto.setError("This url is not present in the db");

            return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
        }

        if(url.getExpirationDate().isBefore(LocalDateTime.now())){
            urlService.deleteShortLink(url);
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setStatus("400");
            urlErrorResponseDto.setError("This url is expired");

            return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
        }

        response.sendRedirect(url.getOriginalLink());
        return null;
    }
}
