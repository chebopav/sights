package com.project.runnables.afishaupdate;

import com.project.entity.afisha.Event;
import com.project.entity.data.Theater;
import com.project.exceptions.DataException;
import com.project.helpers_and_statics.Statics;
import com.project.services.CityService;
import com.project.services.EventService;
import com.project.services.TheaterService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class AfishaUpdate implements Runnable{

    @Autowired
    private EventService service;

    @Autowired
    private CityService cityService;

    @Autowired
    private TheaterService theaterService;

    public AfishaUpdate(EventService service, CityService cityService, TheaterService theaterService) {
        this.service = service;
        this.cityService = cityService;
        this.theaterService = theaterService;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i ++){
            String url = getURL((i+1));
            try {
                stringParsing(url);
            } catch (DataException | IOException e){
                e.printStackTrace();
            }
        }
    }

    private static String getURL(int page){
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = Statics.UPDATE_DATE.format(formatter);
        sb.append("https://www.bileter.ru/afisha?date_from=")
                .append(date)
                .append("&date_to=")
                .append(date)
                .append("&page=")
                .append(page);
        return sb.toString();
    }

    private void stringParsing(String data) throws DataException, IOException {
        int date = Statics.UPDATE_DATE.getDayOfMonth();
        org.jsoup.nodes.Document document = Jsoup.connect(data).get();
        Elements el = document.getElementsByClass("afishe-index");
        List<String> list = new ArrayList<>();
        for(Element element : el.select("a")){
            list.add(element.text());
        }
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).startsWith("Купить")
                    || list.get(i).startsWith("Билетов")
                    || list.get(i).startsWith(String.valueOf(date))
                    || list.get(i).startsWith("Подробнее")
                    || list.get(i).equals("")){
                list.remove(i);
                i --;
            }

        }
        for (int i = 0; i < list.size(); i+=2) {
            String eventName = list.get(i);
            String place = list.get(i+1);
            Theater theater;
            try{
                theater = theaterService.getTheaterByName(place);
            } catch (DataException e){
                theater = new Theater(place);
                theater.setCity(cityService.getCityByName("Санкт-Петербург"));
                theaterService.addTheater(theater);
            }
            Event event = new Event(eventName, theater, Statics.UPDATE_DATE);
            theater.addEvent(event);
            theaterService.updateTheater(theater);
            service.addEvent(event);
        }
    }
}