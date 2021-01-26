package com.project.runnables.afishaupdate;

import com.project.entity.afisha.Event;
import com.project.entity.data.Excursion;
import com.project.entity.data.NeedDate;
import com.project.entity.data.Theater;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.helpers_and_statics.Statics;
import com.project.repository.NeedDateRepository;
import com.project.repository.TheaterRepository;
import com.project.services.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class MoscowAfishaUpdate implements Runnable{

    @Autowired
    private ApplicationContext context;

    public MoscowAfishaUpdate() {
    }

    public MoscowAfishaUpdate(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            String url = getURL(i);
            try {
                stringParsing(url);
            } catch (IOException | DataException e) {
                e.printStackTrace();
            }
        }

    }

    private static String getURL(int page){
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = Statics.UPDATE_DATE.format(formatter);
        sb.append("https://www.ticketland.ru/search/performance/?page=")
                .append(page)
                .append("&mnd=")
                .append(date)
                .append("&mxd=")
                .append(date);
        return sb.toString();
    }

    private void stringParsing(String url) throws IOException, DataException {
        Document document = Jsoup.connect(url).get();
        Elements el = document.getElementsByClass("col-xs-12 col-sm-8 col-lg-9 p-0 p-sm-3");
        List<String> list = new ArrayList<>();
        for(Element element : el.select("a")){
            list.add(element.text());
        }

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).length()<=2){
                i++;
                continue;
            }
            String eventName = list.get(i);
            i++;
            String place = list.get(i);
            i += 3;
            if (place.contains("Экскурсии автобусные")){
                Statics.addExcursion(context, "Москва", eventName, place, Excursion.Type.BUS);
            } else if (place.contains("Экскурсии пешие")){
                Statics.addExcursion(context, "Москва", eventName, place, Excursion.Type.ON_FOOT);
            } else {
                Statics.addEventToTheater(context, "Москва", eventName, place);
            }
            if (i >= list.size() - 4){
                break;
            }
        }
    }
}
