package com.project.runnables.initialisation_for_test;

import com.project.entity.data.Museum;
import com.project.entity.data.NeedDate;
import com.project.entity.data.Sight;
import com.project.entity.data.address.City;
import com.project.entity.data.address.Country;
import com.project.entity.users.Role;
import com.project.entity.users.User;
import com.project.exceptions.DataException;
import com.project.repository.*;
import com.project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class InitRunnable implements Runnable {

    @Autowired
    private ApplicationContext context;

    @Override
    public void run() {
        CountryRepository countryRepository = context.getBean(CountryRepository.class);
        CityRepository cityRepository = context.getBean(CityRepository.class);
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
        if (!roleRepository.existsById(1)) {
            roleRepository.save(admin);
        }
        if (!roleRepository.existsById(2)) {
            roleRepository.save(user);
        }

        if (countryRepository.getCountryByName(country.getName()) == null) {
            countryRepository.save(country);
        }
        if (cityRepository.getCityByName(spb.getName()) == null) {
            cityRepository.save(spb);
        }
        if (cityRepository.getCityByName(moscow.getName()) == null) {
            cityRepository.save(moscow);
        }


        for (int i = 0; i < 30; i++) {
            NeedDate date = new NeedDate(LocalDate.now().plus(i, ChronoUnit.DAYS));
            if (!dateRepository.existsById(date.getDate())) {
                dateRepository.save(date);
            }
        }

        Museum museum = new Museum();
        museum.setName("Эрмитаж");
        museum.setCity(spb);
        museum.setDescription("Одна из главных достопримечательностей СПб");
        museum.setFullAddress("Дворцовая пл.");
        MuseumRepository museumRepository = context.getBean(MuseumRepository.class);
        if (museumRepository.getMuseumByName(museum.getName()) == null) {
            museumRepository.save(museum);
        }


        Sight sight = new Sight();
        sight.setName("Медный всадник");
        sight.setCity(spb);
        sight.setDescription("Памятник Петру I, расположенный на Сенатской площади");
        sight.setFullAddress("Санкт-Петербург, Сенатская площадь");
        SightRepository sightRepository = context.getBean(SightRepository.class);

        if (sightRepository.getSightByName(sight.getName()) == null) {
            sightRepository.save(sight);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = "admin";
        User superUser = new User("admin", encoder.encode(pwd), "chebopav@bk.ru", "Admin");
        superUser.getRoles().add(admin);
        superUser.getRoles().add(user);
        UserRepository userRepository = context.getBean(UserRepository.class);
        if (!userRepository.existsById(1)) {
            userRepository.save(superUser);
        }

    }
}