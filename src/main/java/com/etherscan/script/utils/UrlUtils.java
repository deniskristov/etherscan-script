package com.etherscan.script.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils
{
    public static List<String> extract(String answer) {
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
}
