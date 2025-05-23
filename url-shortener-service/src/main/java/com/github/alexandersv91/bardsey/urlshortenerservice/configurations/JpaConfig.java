package com.github.alexandersv91.bardsey.urlshortenerservice.configurations;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories( //https://vladmihalcea.com/basejparepository-hypersistence-utils/
        value = "com.github.alexandersv91.bardsey.urlshortenerservice.repositories",
        repositoryBaseClass = BaseJpaRepositoryImpl.class
)
public class JpaConfig {
}
