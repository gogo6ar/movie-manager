package com.example.myspringproject.service;

import com.example.myspringproject.repo.*;
import com.example.myspringproject.web.dto.FilmsDto;
import com.example.myspringproject.web.dto.requests.AddFilmRequest;
import com.example.myspringproject.web.entity.Category;
import com.example.myspringproject.web.entity.Films;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmsServiceImpl implements FilmsService {
    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;
    private final FavouriteFilmsRepository favouriteFilmRepository;
    private final CommentRepository commentRepository;
    private final FilmEmotionRepository filmEmotionRepository;

    @Override
    public Films getItemFilm(JsonNode items) {
        JsonNode d = items;
        String title = d.has("title") ? d.get("title").textValue() : "";

        String idIMDb = d.has("id") ? d.get("id").textValue() : "";
        idIMDb.replaceAll("/title/", "").replaceAll("/", "");

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
                .numberOfEpisodes(numberOfEpisodes)
                .year(year)
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
    public List<FilmsDto> getFilmByTitleFromDataBase(String title) {
        List<FilmsDto> list = new ArrayList<>();
        List<Films> listOfFilms = filmRepository.findAllByTitle(title);
        for (Films films : listOfFilms) {
            list.add(FilmsDto.from(films, films.getComments()));
        }

        return list;
    }

    @Override
    public void addFilms(AddFilmRequest request) throws FileAlreadyExistsException {
        if (!filmRepository.findTitle(request.getTitle()).isEmpty()) {
            throw new FileAlreadyExistsException("This film already exist");
        }

        if (!filmRepository.findIdIMDb(request.getIdIMDb()).isEmpty()) {
            throw new FileAlreadyExistsException("This film already exist");
        }

        Films films = Films.builder()
                .title(request.getTitle())
                .idIMDb(request.getIdIMDb())
                .imgLink(request.getImgLink())
                .numberOfEpisodes(request.getNumberOfEpisodes())
                .titleType(request.getTitleType())
                .year(request.getYear())
                .build();
        Long filmId = filmRepository.save(films).getId();

        String arr[] = request.getCategories().split(", ");
        Category category;
        for (String e : arr) {
            category = new Category();
            category.setFilms(filmRepository.getById(filmId));
            category.setCategory(e);
            categoryRepository.save(category);
        }
    }

    @Override
    public void updateFilm(AddFilmRequest request, Long filmId) throws FileAlreadyExistsException {
        //Userid??

        if (!filmRepository.findTitle(request.getTitle()).isEmpty()) {
            throw new FileAlreadyExistsException("This film already exist");
        }

        if (!filmRepository.findIdIMDb(request.getIdIMDb()).isEmpty()) {
            throw new FileAlreadyExistsException("This film already exist");
        }

        Films films = Films.builder()
                .id(filmId)
                .title(request.getTitle())
                .idIMDb(request.getIdIMDb())
                .imgLink(request.getImgLink())
                .numberOfEpisodes(request.getNumberOfEpisodes())
                .titleType(request.getTitleType())
                .year(request.getYear())
                .build();
        filmRepository.save(films);

        String arr[] = request.getCategories().split(", ");
        Category category;
        for (String e : arr) {
            category = new Category();
            category.setFilms(filmRepository.getById(filmId));
            category.setCategory(e);
            categoryRepository.save(category);
        }
    }

    @Override
    public List<FilmsDto> getFilmsByCategory(String category) {
        List<FilmsDto> listOfFilms = new ArrayList<>();
        List<Long> listOfFilmsId = categoryRepository.findAllByCategory(category);

        for (Long id : listOfFilmsId) {
            Films films = filmRepository.getById(id);
            listOfFilms.add(FilmsDto.from(films, films.getComments()));
        }

        return listOfFilms;
    }

    @Override
    public FilmsDto getById(Long id) {
        Optional<Films> films = filmRepository.findById(id);
        FilmsDto filmsDto = FilmsDto.from(films.get(), films.get().getComments());
        return filmsDto;
    }

    @Override
    public List<FilmsDto> getFilmsFromAPI(String title) throws IOException, InterruptedException {

        title = title.replaceAll(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/find?q=" + title))
                .header("x-rapidapi-key", "749bccedf4msh6631c9a0eb08de2p126947jsn174c4d127f78")
                .header("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        List<FilmsDto> list = new ArrayList<>();

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
                    list.add(FilmsDto.from(filmRepository.save(getItemFilm(item))));
                }
            }

        } catch (IOException e) {
            System.out.println("Exception happened: {}");
        }

        return list;
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
    @Transactional
    public void deleteFilmById(Long id) {
        this.filmEmotionRepository.deleteAllByFilmsId(id);
        this.categoryRepository.deleteAllByFilmsId(id);
        this.favouriteFilmRepository.deleteAllByFilmsId(id);
        this.commentRepository.deleteAllByFilmsId(id);
        this.filmRepository.deleteById(id);
    }
}
