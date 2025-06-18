package com.github.alexandersv91.bardsey.urlshortenerservice.services;

import com.github.alexandersv91.bardsey.urlshortenerservice.dtos.UrlRequest;
import com.github.alexandersv91.bardsey.urlshortenerservice.entities.Url;

public interface UrlService {

    Url getOriginalUrl(String shortUrl);
    Url createShortUrl(UrlRequest urlRequest);

}
