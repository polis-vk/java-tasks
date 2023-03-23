package ru.mail.polis.homework.analyzer;


import org.w3c.dom.Text;

/**
 * Базовый интерфейс фильтра, наследники этого интерфейса должны инкапсулировать в себе всю логику
 * анализа текста.
 * Ниже надо реализовать методы, которые создают фильтры заданного типа (то что они возвращают интерфейс, это как раз
 * прием ООП, где нам не важна конкретная реализация, а важен только контракт, что результат статических методов
 * умеет как-то анализировать текст). Сами статические методы мне нужны для создания тестов,
 * что бы без реальных классов (которые вы напишите) я смог "сэмулировать" их создание.
 *
 * Так же необходимо создать все необходимы методы, которые будут вам нужны для прогона текста
 * по всем фильтрам в классе TextFilterManager
 *
 * 2 тугрика + (2 тугрика за каждый фильтр + 1 тугрик за тест на свой фильтр) ИТОГО 11
 */
public interface TextAnalyzer {
    public boolean analyzeText(String text);
    public FilterType getType();
    static TextAnalyzer createTooLongAnalyzer(long maxLength) {
        TextAnalyzer tooLongAnalyzer = new TooLongTextAnalyzer(maxLength);
        return tooLongAnalyzer;
    }

    static TextAnalyzer createSpamAnalyzer(String[] spam) {
        TextAnalyzer spamTextAnalyzer = new SpamTextAnalyzer(spam);
        return spamTextAnalyzer;
    }

    static TextAnalyzer createNegativeTextAnalyzer() {
        TextAnalyzer negativeTextAnalyzer = new NegativeTextAnalyzer();
        return negativeTextAnalyzer;
    }

    /**
     * придумать свой фильтр
     */
    static TextAnalyzer createRepeatedWordsTextFilter(int maxRepeatCount) {
        TextAnalyzer repeatedWordsTextFilter = new RepeatedWordsTextFilter(maxRepeatCount);
        return repeatedWordsTextFilter;
    }
}
