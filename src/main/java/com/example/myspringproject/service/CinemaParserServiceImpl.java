package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.CinemaFilmDto;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CinemaParserServiceImpl implements CinemaParserService {

    public List<CinemaFilmDto> getCinemaFilms(String date) throws IOException {
        List<CinemaFilmDto> listOfCinemaFilms = new ArrayList<>();
        Document doc = Jsoup.connect("https://patria.md/?c_date=" + date).get();
        Elements newsHeadlines = doc.getElementsByClass("col animate-block animate-block--1");

        newsHeadlines.first().getElementsByClass("name");
        //Title of cinema film
        List<String> listOfTitles = new ArrayList<>();
        for (Element e : newsHeadlines) {
            listOfTitles.add((e.text().split(" Audio:")[0]));
        }

        //img link of cinema film

        newsHeadlines = doc.getElementsByTag("img");
        List<String> listOfImgLinks = new ArrayList<>();

        for (Element e : newsHeadlines) {
            listOfImgLinks.add(e.attr("src"));
        }


//        newsHeadlines = doc.getElementsByTag("img");
//        List<String> listOfImgLinks = new ArrayList<>();
//
//        for (Element e : newsHeadlines) {
//            listOfImgLinks.add(e.attr("src"));
//        }


        for (int i = 0; i < listOfTitles.size(); i++) {
            CinemaFilmDto cinemaFilmDto = new CinemaFilmDto();
            cinemaFilmDto.setTitle(listOfTitles.get(i));
            cinemaFilmDto.setDate(date);
//            cinemaFilmDto.setImgLink();
            listOfCinemaFilms.add(cinemaFilmDto);
        }

        return listOfCinemaFilms;
    }
}
