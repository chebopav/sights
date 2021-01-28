package com.project.runnables.send_mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class Sender {
    private String login;
    private String pwd;

    private Properties properties;

    public Sender(String login, String pwd) {
        this.login = login;
        this.pwd = pwd;

        properties = new Properties();

        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
    }

    public String getLogin() {
        return login;
    }

    public void send(String subject, String text, String fromEmail, String toEmail) {
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, pwd);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(login));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
