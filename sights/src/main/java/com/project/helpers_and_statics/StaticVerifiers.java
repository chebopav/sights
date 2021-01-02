package com.project.helpers_and_statics;

import java.util.Objects;

public class StaticVerifiers {

    /**
     * Метод проверки e-mail
     * @param email email
     * @return рошел проверку или нет
     */
    public static boolean verifiedEMail(String email){
        Objects.requireNonNull(email);
        return  email.contains("@")
                && (email.endsWith(".ru") || email.endsWith(".com") || email.endsWith(".net"));
    }

    /**
     * Метод проверки пароля
     * Проверяет на null, на наличие цифры, наличие буквы, содержание символа
     * @param password пароль
     * @return прошел проверку или нет
     */
    public static boolean verifiedPass(String password){
        Objects.requireNonNull(password);
        return password.contains("\\d")               // проверка на содержание цифры
                && password.contains("\\D")           // проверка на содержание буквы
                && password.contains("\\W");          // проверка на содержание символа
    }
}
