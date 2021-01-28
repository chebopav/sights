package com.project.runnables.send_mail;

import com.project.entity.data.BaseData;
import com.project.entity.data.Museum;
import com.project.entity.data.Sight;
import com.project.entity.data.Theater;
import com.project.entity.users.User;
import com.project.repository.MuseumRepository;
import com.project.repository.SightRepository;
import com.project.repository.TheaterRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendMail implements Runnable{
    private static final Sender SENDER = new Sender("city.inside@bk.ru", "RDMU1aucpa1(");
    private static final String SUBJECT = "Рассылка City Inside";

    @Autowired
    private ApplicationContext context;

    @Override
    public void run() {
        UserRepository userRepository = context.getBean(UserRepository.class);
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            String message = createMessage(user);
            SENDER.send(SUBJECT, message, SENDER.getLogin(), user.getEmail());
        }

    }

    private String createMessage(User user){
        StringBuilder sb = new StringBuilder();
        BaseData forMessage = makeChoice();
        sb.append("Здравствуй дорогой друг ")
                .append(user.getName())
                .append("!\n\n")
                .append("Сегодня City Inside раскроет интересную информацию о:\n\n")
                .append(forMessage.getName())
                .append("\n")
                .append(forMessage.getCity().getName())
                .append("\n")
                .append(forMessage.getCity().getCountry().getName())
                .append("\n\n")
                .append(forMessage.getDescription())
                .append("\n\n");
        return sb.toString();
    }

    private BaseData makeChoice(){
        BaseData result = null;
        String desc = null;
        do {
            int rand = (int) (Math.random() * 3);
            if (rand == 0) {
                result = getRandomSight();
            }
            if (rand == 1) {
                result = getRandomMuseum();
            }
            if (rand == 2) {
                result = getRandomTheater();
            }
            if (result != null){
                desc = result.getDescription();
            }

        } while (desc == null);

        return result;
    }

    private Sight getRandomSight(){
        SightRepository repository = context.getBean(SightRepository.class);
        return repository.getRandomSightFromAll();
    }

    private Museum getRandomMuseum(){
        MuseumRepository repository = context.getBean(MuseumRepository.class);
        return repository.getRandomMuseumFromAll();
    }

    private Theater getRandomTheater(){
        TheaterRepository repository = context.getBean(TheaterRepository.class);
        return repository.getRandomTheaterFromAll();
    }
}
