package com.github.alexandersv91.bardsey.urlshortenerservice.controllers;

import com.github.alexandersv91.bardsey.urlshortenerservice.annotations.IpRateLimit;
import com.github.alexandersv91.bardsey.urlshortenerservice.dtos.UrlRequest;
import com.github.alexandersv91.bardsey.urlshortenerservice.dtos.UrlResponse;
import com.github.alexandersv91.bardsey.urlshortenerservice.mappers.UrlMapper;
import com.github.alexandersv91.bardsey.urlshortenerservice.services.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/url")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UrlRestController {

    UrlService urlService;
    UrlMapper urlMapper;

    @IpRateLimit
    @GetMapping("/{shortUrl}")
    public ResponseEntity<UrlResponse> getOriginalUrl(@PathVariable String shortUrl) {
        return ResponseEntity.ok(urlMapper.toResponse(urlService.getOriginalUrl(shortUrl)));
    }

    @IpRateLimit
    @PostMapping
    public ResponseEntity<UrlResponse> createShortUrl(@RequestBody @Valid UrlRequest urlRequest) {
        return ResponseEntity.ok(urlMapper.toResponse(urlService.createShortUrl(urlRequest)));
    }
}
