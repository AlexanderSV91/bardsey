package com.github.alexandersv91.bardsey.urlshortenerservice.mappers;

import com.github.alexandersv91.bardsey.urlshortenerservice.configurations.MapperSpringConfig;
import com.github.alexandersv91.bardsey.urlshortenerservice.dtos.UrlRequest;
import com.github.alexandersv91.bardsey.urlshortenerservice.dtos.UrlResponse;
import com.github.alexandersv91.bardsey.urlshortenerservice.entities.Url;
import org.mapstruct.Mapper;

@Mapper(config = MapperSpringConfig.class)
public interface UrlMapper {

    Url toEntity(UrlRequest urlRequest);

    UrlRequest toRequest(Url url);

    UrlResponse toResponse(Url url);
}
