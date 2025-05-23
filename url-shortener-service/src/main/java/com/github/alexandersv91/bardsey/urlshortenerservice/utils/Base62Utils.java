package com.github.alexandersv91.bardsey.urlshortenerservice.utils;

import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static lombok.AccessLevel.PRIVATE;

@UtilityClass
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class Base62Utils {

    String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    SecureRandom random = new SecureRandom();
    int DEFAULT_LENGTH = 10;

    public String generateRandomBase62String() {
        return generateRandomBase62String(DEFAULT_LENGTH);
    }

    public String generateRandomBase62String(int length) {
        if (length <= 0) {
            length = DEFAULT_LENGTH;
        }
        return IntStream.range(0, length)
                .mapToObj(_ -> String.valueOf(generateRandomBase62Char()))
                .collect(Collectors.joining());
    }

    private char generateRandomBase62Char() {
        return BASE62_CHARS.charAt(random.nextInt(BASE62_CHARS.length()));
    }
}
