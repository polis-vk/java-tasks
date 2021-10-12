package ru.mail.polis.homework.processor;

/**
 * Базовый интерфейс обработчика текста, наследники этого интерефейса должны инкапсулировать в себе всю логику
 * обработки текста.
 */
public interface StringProcessable {

    String process(String text);

}
