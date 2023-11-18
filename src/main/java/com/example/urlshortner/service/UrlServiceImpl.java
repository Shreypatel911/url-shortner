package com.example.urlshortner.service;

import com.example.urlshortner.model.Url;
import com.example.urlshortner.model.UrlDto;
import com.example.urlshortner.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class UrlServiceImpl implements UrlService {
    @Autowired
    private UrlRepository urlRepository;
    private String encodeUrl(String url){
        String encodeUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodeUrl = Hashing.murmur3_32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();

        return encodeUrl;
    }

    @Override
    public Url generateShortLink(UrlDto urlDto) {
        if(StringUtils.isNotEmpty(urlDto.getUrl())){
            String encodeUrl = encodeUrl(urlDto.getUrl());
            Url finalUrlObj = new Url();
            finalUrlObj.setCreatedDate(LocalDateTime.now());
            finalUrlObj.setOriginalLink(urlDto.getUrl());
            finalUrlObj.setShortLink(encodeUrl);
            finalUrlObj.setExpirationDate(generateExpirationDate(urlDto.getExpirationDate(), finalUrlObj.getCreatedDate()));

            Url urlToRet = persistShortLink(finalUrlObj);
            return urlToRet;
        }
        return null;
    }

    private LocalDateTime generateExpirationDate(String expirationDate, LocalDateTime createdDate) {
        if(StringUtils.isBlank(expirationDate))
            return createdDate.plusSeconds(120);
        LocalDateTime date = LocalDateTime.parse(expirationDate);
        return date;
    }

    @Override
    public Url persistShortLink(Url finalUrlObj) {
        Url urlToRet = urlRepository.save(finalUrlObj);
        return urlToRet;
    }

    @Override
    public Url getEncodedUrl(String url) {
        return null;
    }

    @Override
    public void deleteShortLink(Url url) {

    }
}