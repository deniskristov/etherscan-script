package com.etherscan.script.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class UrlUtils
{
    public static List<String> extractUrl(String answer) {
        String regexString = "\\b(https://|www[.])[A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        Pattern pattern = Pattern.compile(regexString,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(answer);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(answer.substring(matcher.start(0),matcher.end(0)));
        }
        result.stream().forEach(url -> System.out.println(url));
        return result;
    }

    public static Optional<String> extractUri(String answer) {
        if (answer.contains("string :"))
        {
            String uri = answer.split("string \\:")[1].trim();
            return uri.contains("Error:") ? Optional.empty() : Optional.of(uri);
        }
        else
        {
            log.error("Error parsing answer: " + answer);
            return Optional.empty();
        }
    }

    public static String cutNumbers(String url) {
        return url.substring(0, url.lastIndexOf('/') + 1);
    }

    public static String parseExtension(String url) {
        String path = url.substring(url.lastIndexOf('/') + 1);
        if (path.contains(".")) {
            return path.split("\\.")[1];
        }
        return "";
    }

    public static String buildUrl(String originalUrl, int number) {
        String noNumbersUrl = cutNumbers(originalUrl);
        String extension = parseExtension(originalUrl);
        StringBuilder urlToCheck = new StringBuilder(noNumbersUrl).append(number);
        if (StringUtils.hasText(extension)) {
            urlToCheck.append(".").append(extension);
        }
        return urlToCheck.toString();
    }
}
