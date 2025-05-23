package com.github.alexandersv91.bardsey.urlshortenerservice.repositories;

import com.github.alexandersv91.bardsey.urlshortenerservice.entities.Url;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends BaseJpaRepository<Url, Long> {

    Optional<Url> findUrlByShortUrl(String shortUrl);

    Optional<Url> findUrlByOriginalUrl(String originalUrl);

    boolean existsByShortUrl(String shortUrl);
}
