package com.project.runnables.test;

import com.project.entity.data.address.Country;
import com.project.exceptions.DataException;
import com.project.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class Test implements Runnable{

    @Autowired
    private CountryService countryService;

    @Autowired

    private ApplicationContext context;


    @Override
    public void run() {
        countryService = context.getBean(CountryService.class);
        try {
            countryService.addCountry(new Country("Украина"));
        } catch (DataException e) {
            e.printStackTrace();
        }
        System.out.println(countryService.getRepository().findAll());
    }
}
