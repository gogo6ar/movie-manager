package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.FilmsDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmsServiceImpl implements FilmsService {
    @Override
    public FilmsDto getFilmsFromAPI(String title) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/find?q=" + title))
                .header("x-rapidapi-key", "749bccedf4msh6631c9a0eb08de2p126947jsn174c4d127f78")
                .header("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());


        try {
            // create object mapper instance
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json");

            ObjectMapper mapper = new ObjectMapper();

            // convert JSON array to list of books
            List<FilmsDto> books = Arrays.asList(mapper.readValue(Paths.get("books.json").toFile(), FilmsDto[].class));

            // print books
            books.forEach(System.out::println);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
