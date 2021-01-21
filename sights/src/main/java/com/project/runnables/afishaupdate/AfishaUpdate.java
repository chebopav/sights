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
public class AfishaUpdate implements Runnable{

    @Autowired
    private ApplicationContext context;

    public AfishaUpdate(ApplicationContext context) {
        this.context = context;
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
        Document document = Jsoup.connect(data).get();
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
            if (eventName.contains("Автобусная экскурсия") || eventName.contains("City Sightseeing")){
                addExcursion(eventName, place, Excursion.Type.BUS);
            } else if (eventName.contains("Экскурсия") || eventName.contains("Экскурсии")){
                addExcursion(eventName, place, Excursion.Type.ON_FOOT);
            } else{
                addEventToTheater(eventName, place);
            }
        }
    }

    private void addEventToTheater(String eventName, String theaterName) throws DataException {
        Theater theater = null;
        TheaterService theaterService = context.getBean(TheaterService.class);
        TheaterRepository theaterRepository = context.getBean(TheaterRepository.class);
        CityService cityService = context.getBean(CityService.class);
        EventService eventService = context.getBean(EventService.class);
        NeedDateService dateService = context.getBean(NeedDateService.class);
        NeedDate needDate = dateService.getNeedDateByDate(Statics.UPDATE_DATE);
        try {
            theater = theaterService.getTheaterByName(theaterName);
        } catch (DataException e) {
            e.printStackTrace();
        }
        if (theater == null){
            theater = new Theater();
            theater.setName(theaterName);
            theater.setCity(cityService.getCityByName("Санкт-Петербург"));
        }

        Event event = new Event(eventName, theater);
        event.getDates().add(needDate);
        needDate.getEvents().add(event);
        theater.addEvent(event);
        theaterRepository.save(theater);
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
            excursion.setCity(cityService.getCityByName("Санкт-Петербург"));
            excursion.setType(type);
        }
        NeedDate date = dateRepository.findById(Statics.UPDATE_DATE).get();
        excursion.getDates().add(date);
        date.getExcursions().add(excursion);
        excursionService.addExcursion(excursion);
        dateRepository.save(date);
    }
}