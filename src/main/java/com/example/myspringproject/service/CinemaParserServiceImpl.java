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
        List<String> listOfImgLinks = new ArrayList<>();
        Elements imgLinks = doc.select("div.in > div.img > img");
        for (Element img : imgLinks) {
            listOfImgLinks.add(img.attr("src"));
        }

        //time
        List<String> listOfFilmsTime = new ArrayList<>();
        List<ArrayList<String>> listOfListFilmsTime = new ArrayList<>();

//        Elements filmTime = doc.select("div.seanse-time > ul > li > a > span");


        Elements filmTime;

        for (int i = 0; i < listOfTitles.size(); i ++) {
            listOfFilmsTime = new ArrayList<>();
            int count = i + 1;
            filmTime = doc.select("div.tabs-box > div.animate-block--1:nth-child(" + count + ") " +
                    "> div.item > div.in > div.descript " +
                    "> div.seanse-time > ul > li > a > span");

            for (Element e : filmTime) {
                if (!e.text().equals("2D") && !e.text().equals("3D")) {
                    listOfFilmsTime.add(e.text());
                }
            }

            listOfListFilmsTime.add((ArrayList<String>) listOfFilmsTime);

            System.out.println("Count: " +i);
            System.out.println(listOfFilmsTime);
        }

//        for (int i = 0; i < listOfListFilmsTime.size(); i ++) {
//            for (int j = 0; j < listOfFilmsTime.size(); j ++) {
//                {
//                    if (listOfListFilmsTime.get(i).get(j).equals("2D") || listOfListFilmsTime.get(i).get(j).equals("3D")) {
//                        listOfListFilmsTime.get(i).remove(j);
//                        j--;
//                    }
//                }
//            }
//        }

        for (int i = 0; i < listOfTitles.size(); i++) {
            CinemaFilmDto cinemaFilmDto = new CinemaFilmDto();
            cinemaFilmDto.setTitle(listOfTitles.get(i));
            cinemaFilmDto.setDate(date);
            cinemaFilmDto.setImgLink(listOfImgLinks.get(i));
            cinemaFilmDto.setTime(listOfListFilmsTime.get(i));

            listOfCinemaFilms.add(cinemaFilmDto);
        }

        return listOfCinemaFilms;
    }
}
