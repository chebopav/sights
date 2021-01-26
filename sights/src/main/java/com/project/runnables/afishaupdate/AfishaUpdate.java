package com.project.runnables.afishaupdate;

import com.project.entity.afisha.Event;
import com.project.entity.data.Excursion;
import com.project.entity.data.NeedDate;
import com.project.entity.data.Theater;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.helpers_and_statics.Statics;
import com.project.repository.EventRepository;
import com.project.repository.ExcursionRepository;
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
            Event event = context.getBean(EventRepository.class).getEventByName(eventName);
            Excursion excursion = context.getBean(ExcursionRepository.class).findByName(eventName);
            if (event == null && excursion == null) {
                if (eventName.contains("Автобусная экскурсия") || eventName.contains("City Sightseeing")) {
                    Statics.addExcursion(context, "Санкт-Петербург", eventName, place, Excursion.Type.BUS);
                } else if (eventName.contains("Экскурсия") || eventName.contains("Экскурсии")) {
                    Statics.addExcursion(context, "Санкт-Петербург", eventName, place, Excursion.Type.ON_FOOT);
                } else {
                    Statics.addEventToTheater(context, "Санкт-Петербург", eventName, place);
                }
            } else if (excursion != null){
                excursion.getDates().add(context.getBean(NeedDateService.class).getNeedDateByDate(Statics.UPDATE_DATE));
                context.getBean(ExcursionService.class).updateExcursion(excursion);
            } else {
                event.getDates().add(context.getBean(NeedDateService.class).getNeedDateByDate(Statics.UPDATE_DATE));
                context.getBean(EventService.class).updateEvent(event);
            }
        }
    }
}