package com.project.exceptions;

/**
 * Exception чтобы выбрасывался при создании объектов
 * (при валидации и использовании сеттеров)
 * Наследник от exception, для того чтобы при работе потоков по времени
 * или выполнения какой-либо другой операции, в том числе создания объектов,
 * обязательно обрабатывать их с использованием try/catch блоков
 */
public class ArgumentException extends Exception{
    public ArgumentException(String message) {
        super(message);
    }
}
