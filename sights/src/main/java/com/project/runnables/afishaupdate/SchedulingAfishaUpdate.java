package com.project.runnables.afishaupdate;

import com.project.entity.data.address.City;
import com.project.entity.data.address.Country;
import com.project.exceptions.DataException;
import com.project.helpers_and_statics.Statics;
import com.project.services.CityService;
import com.project.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulingAfishaUpdate {
    private TaskExecutor executor;
    private ApplicationContext context;

    @Autowired
    public SchedulingAfishaUpdate(@Qualifier("executor") TaskExecutor executor, ApplicationContext context) {
        this.executor = executor;
        this.context = context;
    }

    @Scheduled(fixedRate = Statics.DAY_IN_MILLIS)
    public void start(){
        CountryService countryService = context.getBean(CountryService.class);
        CityService cityService = context.getBean(CityService.class);
        Country country = new Country("Россия");
        City city = new City("Санкт-Петербург");
        country.addCity(city);
        city.setCountry(country);
        try {
            countryService.addCountry(country);
            cityService.addCity(city);
        } catch (DataException e){
            e.printStackTrace();
        }

        AfishaUpdate update = context.getBean(AfishaUpdate.class);
        executor.execute(update);
    }


}
