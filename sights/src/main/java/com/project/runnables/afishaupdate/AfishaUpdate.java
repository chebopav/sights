package com.project.runnables.afishaupdate;

import com.project.entity.afisha.Event;
import com.project.entity.data.Theater;
import com.project.exceptions.DataException;
import com.project.helpers_and_statics.Statics;
import com.project.services.CityService;
import com.project.services.EventService;
import com.project.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class AfishaUpdate implements Runnable{
    private EditorKit editorKit;
    private Document document;

    @Autowired
    private EventService service;

    @Autowired
    private CityService cityService;

    @Autowired
    private TheaterService theaterService;

    @Override
    public void run() {
        editorKit = new HTMLEditorKit();
        document = editorKit.createDefaultDocument();
        document.putProperty("IgnoreCharsetDirective", true);
        try {
            for (int i = 0; i < 4; i++) {
                String url = getURL((i + 1));
                InputStreamReader reader = getReader(url);
                editorKit.read(reader, document, 0);
                String string = document.getText(0, document.getLength());
                document.remove(0, document.getLength());
                stringParsing(string);
            }
        } catch (IOException | BadLocationException | DataException e){
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//
////        //Reader rd = getReader("https://www.mariinsky.ru/playbill/nextplaybill/?place=theatre"); // Мариинский театр
////        //Reader rd = getReader("https://alexandrinsky.ru/afisha-i-bilety/"); // Александринский театр
////        //Reader rd = getReader("https://bdt.spb.ru/%D0%B0%D1%84%D0%B8%D1%88%D0%B0-1/"); // БДТ
////        //Reader rd = getReader("https://spb.kassir.ru/teatry/aleksandrinskiy-teatr"); // Кассир.ру
////        Reader rd = getReader("https://www.bileter.ru/afisha?date_from=10.01.2021&date_to=10.01.2021&page=3"); // Биллетер.ру
//
//        new Thread(new AfishaUpdate()).start();
//    }


    private InputStreamReader getReader(String url) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        return new InputStreamReader(conn.getInputStream());
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

        //String url = "https://www.bileter.ru/afisha?date_from=10.01.2021&date_to=10.01.2021&page=1";
        return sb.toString();
    }

    private void stringParsing(String data) throws DataException {
        int start = data.indexOf("Афиша мероприятий") + 17;
        int end = data.indexOf("Информационный портал Дирекции театрально-зрелищных касс");
        data = data.substring(start, end);
        data = data.trim();
        data = data.replaceAll("\n\n", "\n");
        data = data.replaceAll("«\n" + "1\n" +  "2\n" + "»\n" + " Наверх", "");
        String[] events = data.split("\n");
        int date = (LocalDate.now().plus(14, ChronoUnit.DAYS)).getDayOfMonth();
        for (int i = 0; i < events.length; i++) {
            if (events[i].startsWith(String.valueOf(date))
                    || events[i].startsWith(" ")
                    || events[i].startsWith("Купить")
                    || events[i].startsWith("Запланированных мероприятий не найдено")){
                continue;
            }
            String name = events[i];
            i += 2;
            String place = events[i];
            i += 2;
            if ("1".equals(name))
                continue;
            Theater theater;
            try {
                theater = theaterService.getTheaterByName(name);

            } catch (DataException e){
                theater = new Theater(place);
            }
            Event event = new Event(name, theater, Statics.UPDATE_DATE);
            event.setCity(cityService.getCityByName("Санкт-Петербург"));
            theater.addEvent(event);
            event.setTheater(theater);
            service.addEvent(event);
        }
    }
}