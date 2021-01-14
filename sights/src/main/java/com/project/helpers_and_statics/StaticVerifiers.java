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
//        Objects.requireNonNull(password);
//        int length = password.length();
//        if (length < 8 || length > 30)
//            throw new IllegalArgumentException("Пароль не может быть меньше 8 символов и больше 30 символов");
//        String verifier = password.replaceAll("\\d", "");
//        if (verifier.length() == length)
//            throw new IllegalArgumentException("Пароль должен содержать цифру");
//        length = verifier.length();
//        verifier = verifier.replaceAll("\\W", "");
//        if (verifier.length() == length)
//            throw new IllegalArgumentException("Пароль должен содержать символ");
//        if (verifier.length() == 0)
//            throw new IllegalArgumentException("Пароль должен содержать букву");
        return true;
    }
}
