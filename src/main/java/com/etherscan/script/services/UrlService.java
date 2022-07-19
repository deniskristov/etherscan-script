package com.etherscan.script.services;

import com.etherscan.script.utils.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UrlService
{
    private HttpClient httpClient = HttpClient.newHttpClient();

    public List<Integer> findKeyWordsFromStartToEnd(String url, String keyWord, Integer start, Integer end)
    {
        List<Integer> result = new ArrayList<>();
        String noNumbersUrl = UrlUtils.cutNumbers(url);
        for (int i = start; i < end + 1; i++)
        {
            String urlToCheck = noNumbersUrl + i;
            try
            {
                HttpRequest.Builder request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(urlToCheck));
                HttpResponse<String> response = httpClient.send(request.build(), HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == HttpURLConnection.HTTP_OK)
                {
                    if (response.body().contains(keyWord))
                    {
                        result.add(i);
                    }
                }
                else
                {
                    log.warn("HTTP error " + response.statusCode() + " while reading " + urlToCheck);
                }
            }
            catch (Exception e) {
                log.error("Error reading " + urlToCheck, e);
            }

        }
        return result;
    }
}
