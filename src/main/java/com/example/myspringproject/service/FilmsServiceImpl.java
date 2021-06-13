package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.FilmsDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javassist.bytecode.ByteArray;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmsServiceImpl implements FilmsService {
    public void jsonParse(@RequestBody HttpRequest request) {
        System.out.println(request.bodyPublisher().get());
    }

    private static final ObjectMapper mapper = new ObjectMapper();


    @Override
    public FilmsDto getFilmsFromAPI(String title) throws IOException, InterruptedException, UnirestException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/find?q=" + title))
                .header("x-rapidapi-key", "749bccedf4msh6631c9a0eb08de2p126947jsn174c4d127f78")
                .header("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        JsonNode root = mapper.readTree();


        return null;
    }
}
