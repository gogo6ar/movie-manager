package com.example.myspringproject.service;

import com.example.myspringproject.repo.FilmRepository;
import com.example.myspringproject.web.dto.FilmsDto;
import com.example.myspringproject.web.entity.Films;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmsServiceImpl implements FilmsService {
    private final FilmRepository filmRepository;

    @Override
    public Films getItemFilm(JsonNode items) {
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

        Films film = Films.builder()
                .idIMDb(idIMDb)
                .title(title)
                .numberOfEpisodes(String.valueOf(numberOfEpisodes))
                .year(String.valueOf(year))
                .titleType(titleType)
                .imgLink(imgLink).build();
        return film;
    }

    @Override
    public Films getFilmByIdIMDb(String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/get-details?tconst=" + id))
                .header("x-rapidapi-key", "749bccedf4msh6631c9a0eb08de2p126947jsn174c4d127f78")
                .header("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

        // Dont fail for not mapped values
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        // Fail if primitive values are null
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        try {
            JsonNode jsonNode = mapper.readTree(response.body().toString());
            if (jsonNode != null) {
                return getItemFilm(jsonNode);
            }

        } catch (IOException e) {
            System.out.println("Exception happened: {}");
        }

        return null;
    }

    @Override
    public void saveFilm(Films film) {
        filmRepository.save(film);
    }

    @Override
    public Map<Byte, String> getTop100Films() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/" +
                        "get-most-popular-movies?homeCountry=US&purchaseCountry=US&currentCountry=US"))
                .header("x-rapidapi-key", "749bccedf4msh6631c9a0eb08de2p126947jsn174c4d127f78")
                .header("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

        // Dont fail for not mapped values
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        // Fail if primitive values are null
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        Map<Byte, String> top100 = new LinkedHashMap<>();
        byte i = 1;
        try {
            JsonNode jsonNode = mapper.readTree(response.body().toString()
                    .replaceAll("/title/", "")
                    .replaceAll("/", ""));
            if (jsonNode != null && jsonNode.isArray()) {
                for (JsonNode item : jsonNode) {
                    top100.put(i, item.textValue());
                    i++;
                }
            }

        } catch (IOException e) {
            System.out.println("Exception happened: {}");
        }

        return top100;
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

        ObjectMapper mapper = new ObjectMapper();

        // Dont fail for not mapped values
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        // Fail if primitive values are null
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        try {
            JsonNode jsonNode = mapper.readTree(response.body().toString());
            JsonNode items = jsonNode.get("results");
            if (items != null && items.isArray()) {
                for (JsonNode item : items) {
                    saveFilm(getItemFilm(item));
                }
            }

        } catch (IOException e) {
            System.out.println("Exception happened: {}");
        }

        return null;
    }

    public List<FilmsDto> findAll() {
        List<Films> films = filmRepository.findAll();
        List<FilmsDto> filmsDtos = new ArrayList<>();
        for (Films film : films) {
            FilmsDto filmsDto = FilmsDto.from(film, film.getComments());
            filmsDtos.add(filmsDto);
        }
        return filmsDtos;
    }

    @Override
    public void deleteFilmById(Long id) {
        this.filmRepository.deleteById(id);
    }
}
