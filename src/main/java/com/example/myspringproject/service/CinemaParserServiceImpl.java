package com.example.myspringproject.service;

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
public class CinemaParserServiceImpl implements CineParserService {

    public void saveCinemaFilms() throws IOException {
        Document doc = Jsoup.connect("https://patria.md/?c_date=2021-06-18").get();
        System.out.println((doc.title()));
        Elements newsHeadlines = doc.select("name");
        newsHeadlines = doc.getElementsByClass("name");
        System.out.println(newsHeadlines);
        List<String> list = new ArrayList<>();
//        newsHeadlines = doc.get
        for (Element headline : newsHeadlines) {
//            System.out.println(headline);
//            System.out.println("---------------------");
            list.add(headline.toString().replaceAll("<div class=\"name\">", "").replaceAll("</div>", ""));
        }

        System.out.println(list);;

    }
}
