package com.github.alexandersv91.bardsey.urlshortenerservice.services;

import com.github.alexandersv91.bardsey.urlshortenerservice.dto.UrlRequest;
import com.github.alexandersv91.bardsey.urlshortenerservice.entities.Url;
import com.github.alexandersv91.bardsey.urlshortenerservice.exceptions.NotFoundShortenerUrlException;
import com.github.alexandersv91.bardsey.urlshortenerservice.exceptions.UrlGenerationException;
import com.github.alexandersv91.bardsey.urlshortenerservice.repositories.UrlRepository;
import com.github.alexandersv91.bardsey.urlshortenerservice.utils.Base62Utils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.alexandersv91.bardsey.urlshortenerservice.configurations.CachingConfig.SHORT_URLS_CACHE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@Transactional(readOnly = true)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    static int MAX_GENERATION_ATTEMPTS = 5;

    UrlRepository urlRepository;

    @Override
    @Cacheable(value = SHORT_URLS_CACHE, key = "#shortUrl")
    public Url getOriginalUrl(String shortUrl) {
        if (StringUtils.isBlank(shortUrl)) {
            throw new IllegalArgumentException("Short URL cannot be blank");
        }
        return this.urlRepository.findUrlByShortUrl(shortUrl).orElseThrow(NotFoundShortenerUrlException::new);
    }

    @Override
    @Transactional
    public Url createShortUrl(UrlRequest urlRequest) {
        var originalUrl = this.urlRepository.findUrlByOriginalUrl(urlRequest.originalUrl());
        if (originalUrl.isPresent()) {
            return originalUrl.get();
        }
        var url = Url.builder()
                .originalUrl(urlRequest.originalUrl())
                .shortUrl(this.getUniqueShortUrl())
                .build();
        return this.urlRepository.persist(url);
    }

    private String getUniqueShortUrl() {
        for (int attempt = 0; attempt < MAX_GENERATION_ATTEMPTS; attempt++) {
            String candidateUrl = Base62Utils.generateRandomBase62String();
            if (!urlRepository.existsByShortUrl(candidateUrl)) {
                return candidateUrl;
            }
        }
        throw new UrlGenerationException("Failed to generate unique short URL after " + MAX_GENERATION_ATTEMPTS + " attempts");
    }

}
