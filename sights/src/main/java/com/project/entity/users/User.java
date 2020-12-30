package com.project.entity.users;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс Юзеров
 * Не вижу смысла делить юзеров на обычных и суперюзеров
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 30, unique = true)
    private String login;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    private Status status;

    @Column(unique = true)
    private String photoURL;

    public enum Status{
        ADMIN,
        USER
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Status getStatus() {
        return status;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setLogin(String login) {
        if (login == null || login.trim().length() < 3)
            throw new IllegalArgumentException("Некорректный логин");
        this.login = login;
    }

    public void setPassword(String password) {
        if (!verifiedPass(password)) {
            throw new IllegalArgumentException("Недопустимый пароль");
        }
        this.password = password;
    }

    public void setEmail(String email) {
        if (!verifiedEMail(email))
            throw new IllegalArgumentException("Недопустимый пароль");
        this.email = email;
    }

    public void setStatus(Status status) {
        Objects.requireNonNull(status);
        this.status = status;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    /**
     * Метод проверки пароля
     * Проверяет на null, на наличие цифры, наличие буквы, содержание символа
     * @param password пароль
     * @return прошел проверку или нет
     */
    private boolean verifiedPass(String password){
        Objects.requireNonNull(password);
        return password.contains("\\d")               // проверка на содержание цифры
                && password.contains("\\D")           // проверка на содержание буквы
                && password.contains("\\W");          // проверка на содержание символа
    }

    /**
     * Метод проверки e-mail
     * @param email email
     * @return рошел проверку или нет
     */
    private boolean verifiedEMail(String email){
        Objects.requireNonNull(email);
        return  email.contains("@")
                && (email.endsWith(".ru") || email.endsWith(".com") || email.endsWith(".net"));
    }
}
