package org.example.model.downloading;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDownloader {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String USER_AGENT = "MyTaskLab3/1.0";


    public static CompletableFuture<Map<String, Object>> download(String url){

        String fullUrl = url;

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fullUrl)).header("User-Agent",USER_AGENT).GET().build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
            if(response.statusCode() >= 400){
                throw new CompletionException(
                        new HttpExeption("HTTP error: " + response.statusCode())
                );
            }
            try {
                return objectMapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        });
    }
}
