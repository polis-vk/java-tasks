package ru.mail.polis.homework.analyzer;


import ru.mail.polis.homework.analyzer.filters.CustomFilter;
import ru.mail.polis.homework.analyzer.filters.NegativeTextFilter;
import ru.mail.polis.homework.analyzer.filters.SpamFilter;
import ru.mail.polis.homework.analyzer.filters.TooLongFilter;

/**
 * Базовый интерефейс фильтра, наследники этого интерефейса должны инкапсулировать в себе всю логику
 * анализа текста.
 * Ниже надо реализовать методы, которые создают фильтры заданного типа (то что они возвращают интерфейс, это как раз
 * прием ООП, где нам не важна конкретная реализация, а важен только контракт, что результат статических методов
 * умеет как-то анализировать текст). Сами статические методы мне нужны для создания тестов,
 * что бы без реальных классов (которые вы напишите) я смог "сэмулировать" их создание.
 * <p>
 * Так же необходимо создать все необходимы методы, которые будут вам нужны для прогона текста
 * по всем фильтрам в классе TextFilterManager
 * <p>
 * 2 балла + (2 балла за каждый фильтр + 1 балл за тест на свой фильтр) ИТОГО 11
 */
public interface TextAnalyzer {

    static TextAnalyzer createTooLongAnalyzer(long maxLength) {
        return new TooLongFilter(maxLength);
    }

    static TextAnalyzer createSpamAnalyzer(String[] spam) {
        return new SpamFilter(spam);
    }

    static TextAnalyzer createNegativeTextAnalyzer() {
        return new NegativeTextFilter();
    }

    static TextAnalyzer createCustomAnalyzer() {
        return new CustomFilter();
    }

    FilterType analyze(String str);

    FilterType getValue();
}
