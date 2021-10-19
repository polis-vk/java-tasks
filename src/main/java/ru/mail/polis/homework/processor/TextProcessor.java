package ru.mail.polis.homework.processor;

import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.Locale;

/**
 * Базовый интерфейс обработчика текста, наследники этого интерефейса должны инкапсулировать в себе всю логику
 * обработки текста.
 * Ниже надо реализовать методы, которые создают обработчики заданного типа (то что они возвращают интерфейс,
 * это как раз прием ООП, где нам не важна конкретная реализация, а важен только контракт,
 * что результат статических методов умеет как-то обрабатывать текст).
 * <p>
 * Сами статические методы мне нужны для создания тестов, что бы без реальных классов (которые вы напишите)
 * я смог "сэмулировать" их создание.
 * <p>
 * Каждый обработчик 2 балла. Итого 8
 */
public interface TextProcessor {
    public String process(String text);
    public ProcessingStage getStage();
    /**
     * Схлопывает все пустые символы в один пробел.
     * Более формально, заменить каждую подстроку, удовлетворяющую регулярному выражению \s+ на 1 пробел.
     * <p>
     * Стадия: препроцессинг
     */
    static TextProcessor squashWhiteSpacesProcessor() {
        return new SquashWhiteSpacesProcessor();
    }

    /**
     * Находит первую подстроку, которая удовлетвроряет регулярному выражению regex, и заменяет ее на подстроку replacement
     * Предполагаем, что параметры корректны
     * <p>
     * Стадия: процессинг
     */
    static TextProcessor replaceFirstProcessor(String regex, String replacement) {
        return new ReplaceFirstProcessor(regex, replacement);
    }

    /**
     * Данный обработчик должен оставить первые maxLength символов исходного текста.
     * Если текст короче, то ничего не делать
     * <p>
     * Стадия: постпроцессинг
     *
     * @param maxLength неотрицательное число
     */
    static TextProcessor trimProcessor(int maxLength) {
        return new TrimProcessor(maxLength);
    }

    /**
     * Обработчик заменяет все символы на заглавные
     * <p>
     * Стадия: постпроцессинг
     */
    static TextProcessor upperCaseProcessor() {
        return new UpperCaseProcessor();
    }

}

class SquashWhiteSpacesProcessor implements TextProcessor {
    private final static ProcessingStage stage = ProcessingStage.PREPROCESSING;

    @Override
    public String process(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("\\s+", " ");
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}

class ReplaceFirstProcessor implements TextProcessor {
    private final static ProcessingStage stage = ProcessingStage.PROCESSING;
    String regex;
    String replacement;

    public ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String process(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceFirst(regex, replacement);
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}

class TrimProcessor implements TextProcessor {
    private final static ProcessingStage stage = ProcessingStage.POSTPROCESSING;
    int maxLength;

    public TrimProcessor(int maxLength) {
        if (maxLength < 0) {
            throw new IllegalArgumentException();
        }
        this.maxLength = maxLength;
    }

    @Override
    public String process(String text) {
        if (text == null) {
            return null;
        }
        if (maxLength > text.length()){
            return text;
        }
        return text.substring(0, maxLength);
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}

class UpperCaseProcessor implements TextProcessor {
    private final static ProcessingStage stage = ProcessingStage.POSTPROCESSING;

    @Override
    public String process(String text) {
        if (text == null) {
            return null;
        }
        return text.toUpperCase();
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
