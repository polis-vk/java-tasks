package ru.mail.polis.homework.analyzer;


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
        return new TooLongAnalyzer(maxLength);
    }

    static TextAnalyzer createSpamAnalyzer(String[] spam) {
        return new SpamAnalyzer(spam);
    }

    static TextAnalyzer createNegativeTextAnalyzer() {
        return new NegativeTextAnalyzer();
    }

    /**
     * придумать свой фильтр
     */
    static TextAnalyzer createCapsLockAnalyzer() {
        return new CapsLockAnalyzer();
    }

    FilterType check(String txt);

    static boolean find(String[] targetSymb, String txt) {
        for (String currentSymb : targetSymb) {
            if (txt.contains(currentSymb)) {
                return true;
            }
        }
        return false;
    }
}
