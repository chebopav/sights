package com.project.runnables.afishaupdate;

import com.project.entity.afisha.Event;
import com.project.entity.data.Excursion;
import com.project.entity.data.NeedDate;
import com.project.entity.data.Theater;
import com.project.exceptions.DataException;
import com.project.helpers_and_statics.Statics;
import com.project.repository.NeedDateRepository;
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
                addExcursion(eventName, place, Excursion.Type.BUS);
            } else if (place.contains("Экскурсии пешие")){
                addExcursion(eventName, place, Excursion.Type.ON_FOOT);
            } else{
                addEventToTheater(eventName, place);
            }

            if (i >= list.size() - 4){
                break;
            }
        }
    }

    private void addEventToTheater(String eventName, String theaterName) throws DataException {
        Theater theater;
        TheaterService theaterService = context.getBean(TheaterService.class);
        CityService cityService = context.getBean(CityService.class);
        EventService eventService = context.getBean(EventService.class);
        NeedDateService dateService = context.getBean(NeedDateService.class);
        NeedDate needDate = dateService.getNeedDateByDate(Statics.UPDATE_DATE);
        try {
            theater = theaterService.getTheaterByName(theaterName);
        } catch (DataException e) {
            theater = new Theater(theaterName);
            theater.setCity(cityService.getCityByName("Москва"));
            theaterService.addTheater(theater);
        }
        Event event = new Event(eventName, theater);
        event.getDates().add(needDate);
        needDate.getEvents().add(event);
        theater.addEvent(event);
        theaterService.updateTheater(theater);
        eventService.addEvent(event);
        dateService.updateDate(needDate);
    }

    private void addExcursion(String excursionName, String description, Excursion.Type type) throws DataException {
        Excursion excursion;
        ExcursionService excursionService = context.getBean(ExcursionService.class);
        CityService cityService = context.getBean(CityService.class);
        NeedDateRepository dateRepository = context.getBean(NeedDateRepository.class);
        try{
            excursion = excursionService.getExcursionByName(excursionName);
        } catch (DataException e){
            excursion = new Excursion();
            excursion.setName(excursionName);
            excursion.setDescription(description);
            excursion.setCity(cityService.getCityByName("Москва"));
            excursion.setType(type);
        }
        NeedDate date = dateRepository.findById(Statics.UPDATE_DATE).get();
        excursion.getDates().add(date);
        date.getExcursions().add(excursion);
        excursionService.addExcursion(excursion);
        dateRepository.save(date);
    }
}
