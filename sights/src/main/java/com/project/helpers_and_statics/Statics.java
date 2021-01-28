package com.project.helpers_and_statics;

import com.project.entity.afisha.Event;
import com.project.entity.data.Excursion;
import com.project.entity.data.NeedDate;
import com.project.entity.data.Theater;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.NeedDateRepository;
import com.project.repository.TheaterRepository;
import com.project.services.*;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Statics {

    public static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24L;

    public static final LocalDate UPDATE_DATE = LocalDate.now().plus(14, ChronoUnit.DAYS);

    public static LocalDate makeDateFromString(String string){
        String[] forDate = string.split("\\.");
        int day = Integer.parseInt(forDate[0]);
        int month = Integer.parseInt(forDate[1]);
        int year = Integer.parseInt(forDate[2]);
        return LocalDate.of(year, month, day);
    }

    public static void addEventToTheater(ApplicationContext context, String cityName, String eventName, String theaterName) throws DataException {
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
            City city = cityService.getCityByName(cityName);
            theater = new Theater(theaterName, city);
            theaterService.addTheater(theater);
            cityService.updateCity(city);
        }

        Event event = new Event(eventName, theater);
        event.getDates().add(needDate);
        needDate.getEvents().add(event);
        theater.addEvent(event);
        theaterRepository.save(theater);
        eventService.addEvent(event);
        dateService.updateDate(needDate);
    }

    public static void addExcursion(ApplicationContext context, String cityName, String excursionName, String description, Excursion.Type type) throws DataException {
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
            excursion.setCity(cityService.getCityByName(cityName));
            excursion.setType(type);
        }
        NeedDate date = dateRepository.findById(Statics.UPDATE_DATE).get();
        excursion.getDates().add(date);
        date.getExcursions().add(excursion);
        excursionService.addExcursion(excursion);
        dateRepository.save(date);
    }

}
