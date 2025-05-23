package com.github.alexandersv91.bardsey.urlshortenerservice.annotations;

import com.github.alexandersv91.bardsey.urlshortenerservice.exceptions.IpRateLimitException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class IpRateLimiterAspectComponent {

    private final Map<String, IpRateLimitData> ipRateLimitDataMap = new HashMap<>();

    @Around("@annotation(ipRateLimit)")
    public Object limit(ProceedingJoinPoint joinPoint, IpRateLimit ipRateLimit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        log.info("IP '{}' request", ip);
        String key = "hash-" + Base64.getEncoder().encodeToString(ip.getBytes());

        if (isRateLimited(key, ipRateLimit.limit(), ipRateLimit.duration())) {
            log.info("IP '{}' has request limit", ip);
            throw new IpRateLimitException("Rate limit exceeded for IP: " + ip);
        }
        return joinPoint.proceed();
    }

    public boolean isRateLimited(String key, int limit, int duration) {
        IpRateLimitData ipRateLimitData = ipRateLimitDataMap.get(key);

        boolean finalAns = false;

        if (ipRateLimitData == null) {
            ipRateLimitData = IpRateLimitData.setDefaultData();
        } else {
            if (ipRateLimitData.getAmount() >= limit) {
                if (ipRateLimitData.getTimeDiffInSeconds() < duration) {
                    finalAns = true;
                } else {
                    ipRateLimitData = IpRateLimitData.setDefaultData();
                }
            } else {
                ipRateLimitData.setAmount(ipRateLimitData.getAmount() + 1);
            }
        }

        log.info("Key '{}' sent requests '{}' times", key, ipRateLimitData.getAmount());

        ipRateLimitDataMap.put(key, ipRateLimitData);

        return finalAns;
    }
}
