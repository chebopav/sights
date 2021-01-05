package com.project.exceptions;

/**
 * Данный exception, чтобы его обрабатывать в рамках получения сведений из БД
 * с помощью сервисов
 * Наследник от простого exception, чтобы всегда обрабатывать в блоке try/catch
 */
public class DataException extends Exception{

    public DataException(String message) {
        super(message);
    }
}
