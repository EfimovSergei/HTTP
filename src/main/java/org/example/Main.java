package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main {
    public static final String REMOTE_SERVICE_URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        HttpGet request = new HttpGet(REMOTE_SERVICE_URL);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        CloseableHttpResponse response = httpClient.execute(request);
        InputStream content = response.getEntity().getContent();
        List<Post> posts = mapper.readValue(content, new TypeReference<List<Post>>() {
        });
   posts.stream()
           .filter(value->value.getUpvotes()!=null)
           .forEach(System.out::println);
    }
}