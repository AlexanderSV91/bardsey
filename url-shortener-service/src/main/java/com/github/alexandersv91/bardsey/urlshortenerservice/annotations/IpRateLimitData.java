package com.github.alexandersv91.bardsey.urlshortenerservice.annotations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IpRateLimitData {

    private int amount;
    private LocalDateTime requestDate;

    public static IpRateLimitData setDefaultData() {
        return new IpRateLimitData(1, LocalDateTime.now());
    }

    public long getTimeDiffInSeconds() {
        return requestDate.until(LocalDateTime.now(), SECONDS);
    }
}
