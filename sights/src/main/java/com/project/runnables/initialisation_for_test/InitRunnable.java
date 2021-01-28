package com.project.runnables.initialisation_for_test;

import com.project.entity.data.Museum;
import com.project.entity.data.NeedDate;
import com.project.entity.data.Sight;
import com.project.entity.data.address.City;
import com.project.entity.data.address.Country;
import com.project.entity.users.Role;
import com.project.entity.users.User;
import com.project.exceptions.DataException;
import com.project.repository.NeedDateRepository;
import com.project.repository.RoleRepository;
import com.project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class InitRunnable implements Runnable{

    @Autowired
    private ApplicationContext context;

    @Override
    public void run() {
        CountryService countryService = context.getBean(CountryService.class);
        CityService cityService = context.getBean(CityService.class);
        NeedDateRepository dateRepository = context.getBean(NeedDateRepository.class);
        Country country = new Country("Россия");
        City spb = new City("Санкт-Петербург");
        City moscow = new City("Москва");
        country.addCity(spb);
        country.addCity(moscow);
        spb.setCountry(country);
        moscow.setCountry(country);
        RoleRepository roleRepository = context.getBean(RoleRepository.class);
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");
        roleRepository.save(admin);
        roleRepository.save(user);
        try {
            countryService.addCountry(country);
            cityService.addCity(spb);
            cityService.addCity(moscow);
        } catch (DataException e){
            e.printStackTrace();
        }

        for (int i = 0; i < 30; i++) {
            NeedDate date = new NeedDate(LocalDate.now().plus(i, ChronoUnit.DAYS));
            dateRepository.save(date);
        }

        Museum museum = new Museum();
        museum.setName("Эрмитаж");
        museum.setCity(spb);
        museum.setDescription("Одна из главных достопримечательностей СПб");
        museum.setFullAddress("Дворцовая пл.");
        MuseumService museumService = context.getBean(MuseumService.class);
        try {
            museumService.addMuseum(museum);
        } catch (DataException e) {
            e.printStackTrace();
        }

        Sight sight = new Sight();
        sight.setName("Медный всадник");
        sight.setCity(spb);
        sight.setDescription("Памятник Петру I, расположенный на Сенатской площади");
        sight.setFullAddress("Санкт-Петербург, Сенатская площадь");
        SightService sightService = context.getBean(SightService.class);
        try {
            sightService.addSight(sight);
        } catch (DataException e) {
            e.printStackTrace();
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = "admin";
        User superUser = new User("admin", encoder.encode(pwd), "chebopav@bk.ru", "Admin");
        superUser.getRoles().add(admin);
        superUser.getRoles().add(user);
        UserService userService = context.getBean(UserService.class);
        try {
            userService.addUser(superUser);
        } catch (DataException e) {
            e.printStackTrace();
        }
    }
}