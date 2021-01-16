package com.project.runnables.afishaupdate;

import com.project.entity.data.NeedDate;
import com.project.entity.data.address.City;
import com.project.entity.data.address.Country;
import com.project.entity.users.Role;
import com.project.exceptions.DataException;
import com.project.helpers_and_statics.Statics;
import com.project.repository.NeedDateRepository;
import com.project.repository.RoleRepository;
import com.project.services.CityService;
import com.project.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
        NeedDateRepository dateRepository = context.getBean(NeedDateRepository.class);
        Country country = new Country("Россия");
        City city = new City("Санкт-Петербург");
        country.addCity(city);
        city.setCountry(country);
        RoleRepository roleRepository = context.getBean(RoleRepository.class);
        roleRepository.save(new Role("ROLE_USER"));
        roleRepository.save(new Role("ROLE_ADMIN"));
        try {
            countryService.addCountry(country);
            cityService.addCity(city);
        } catch (DataException e){
            e.printStackTrace();
        }

        for (int i = 0; i < 30; i++) {
            NeedDate date = new NeedDate(LocalDate.now().plus(i, ChronoUnit.DAYS));
            dateRepository.save(date);
        }


        AfishaUpdate update = context.getBean(AfishaUpdate.class);
        executor.execute(update);
    }


}