-- liquibase formatted sql

-- changeset Alexander:1747555950833-1
CREATE SEQUENCE  IF NOT EXISTS url_seq START WITH 1 INCREMENT BY 50;

-- changeset Alexander:1747555950833-2
CREATE TABLE urls (id BIGINT NOT NULL, original_url VARCHAR(255) NOT NULL, short_url VARCHAR(255) NOT NULL, created_date TIMESTAMP WITHOUT TIME ZONE, CONSTRAINT pk_urls PRIMARY KEY (id));

-- changeset Alexander:1747555950833-3
ALTER TABLE urls ADD CONSTRAINT uc_urls_short_url UNIQUE (short_url);

-- changeset Alexander:1747555950833-4
CREATE INDEX idx_urls_short_url_unq_hash ON urls USING hash (short_url);
