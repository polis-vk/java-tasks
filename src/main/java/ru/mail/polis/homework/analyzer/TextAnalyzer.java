package ru.mail.polis.homework.analyzer;


/**
 * Базовый интерфейс фильтра, наследники этого интерфейса должны инкапсулировать в себе всю логику
 * анализа текста.
 * Ниже надо реализовать методы, которые создают фильтры заданного типа (то что они возвращают интерфейс, это как раз
 * прием ООП, где нам не важна конкретная реализация, а важен только контракт, что результат статических методов
 * умеет как-то анализировать текст). Сами статические методы мне нужны для создания тестов,
 * что бы без реальных классов (которые вы напишите) я смог "сэмулировать" их создание.
 * <p>
 * Так же необходимо создать все необходимы методы, которые будут вам нужны для прогона текста
 * по всем фильтрам в классе TextFilterManager
 * <p>
 * 2 тугрика + (2 тугрика за каждый фильтр + 1 тугрик за тест на свой фильтр) ИТОГО 11
 */
public interface TextAnalyzer {

    static TextAnalyzer createTooLongAnalyzer(long maxLength) {
        return new LongFilterAnalyzer(maxLength);
    }

    static TextAnalyzer createSpamAnalyzer(String[] spam) {
        return new SpamFilterAnalyzer(spam);
    }

    static TextAnalyzer createNegativeTextAnalyzer() {
        return new BadEmojiFilterAnalyzer();
    }

    static TextAnalyzer createTooMuchWordsAnalyzer(long something) {
        return new TooMuchWordsAnalyzer(something);
    }

    FilterType getType();

    boolean isFilterPassed(String text);
}
