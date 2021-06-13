package com.example.myspringproject.service;

import com.example.myspringproject.repo.FilmRepository;
import com.example.myspringproject.web.dto.FilmsDto;
import com.example.myspringproject.web.entity.Films;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmsServiceImpl implements FilmsService {
    private final FilmRepository filmRepository;

    public void saveFilm(JsonNode items) {
        StringBuilder categoriesSb = new StringBuilder();

        JsonNode d = items;
        String title = d.has("title") ? d.get("title").textValue() : "";

        String idIMDb = d.has("id") ? d.get("id").textValue() : "";

        JsonNode img = d.has("image") ? d.get("image") : d.get(null);
        String imgLink;
        if (img != null) {
            imgLink = img.has("url") ? img.get("url").textValue() : "";
        }
        else {
            imgLink = "";
        }

        int numberOfEpisodes = d.has("numberOfEpisodes") ? d.get("numberOfEpisodes").asInt() : 1;
        String titleType = d.has("titleType") ? d.get("titleType").textValue() : "";
        int year = d.has("year") ? d.get("year").asInt() : 0;

        Films films = Films.builder()
                .idIMDb(idIMDb)
                .title(title)
                .numberOfEpisodes(String.valueOf(numberOfEpisodes))
                .year(String.valueOf(year))
                .titleType(titleType)
                .imgLink(imgLink).build();

        filmRepository.save(films);
    }

    @Override
    public FilmsDto getFilmsFromAPI(String title) throws IOException, InterruptedException {

        title = title.replaceAll(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/find?q=" + title))
                .header("x-rapidapi-key", "749bccedf4msh6631c9a0eb08de2p126947jsn174c4d127f78")
                .header("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();

        // Dont fail for not mapped values
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        // Fail if primitive values are null
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        FilmsDto book = FilmsDto.builder().build();
        ArrayList<FilmsDto> films = new ArrayList<>();
        try {
            //JsonNode jsonNode = mapper.readValue(responseEntity.getBody(), JsonNode.class);
            JsonNode jsonNode = mapper.readTree(response.body().toString());
            JsonNode items = jsonNode.get("results");
            if (items != null && items.isArray()) {
                for (JsonNode item : items) {
                    saveFilm(item);
                }
            }

        } catch (IOException e) {
            System.out.println("Exception happened: {}");
        }

        return null;
    }
}
