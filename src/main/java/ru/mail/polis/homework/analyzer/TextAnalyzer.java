package ru.mail.polis.homework.analyzer;


/**
 * Базовый интерефейс фильтра, наследники этого интерефейса должны инкапсулировать в себе всю логику
 * анализа текста.
 * Ниже надо реализовать методы, которые создают фильтры заданного типа (то что они возвращают интерфейс, это как раз
 * прием ООП, где нам не важна конкретная реализация, а важен только контракт, что результат статических методов
 * умеет как-то анализировать текст). Сами статические методы мне нужны для создания тестов,
 * что бы без реальных классов (которые вы напишите) я смог "сэмулировать" их создание.
 *
 * Так же необходимо создать все необходимы методы, которые будут вам нужны для прогона текста
 * по всем фильтрам в классе TextFilterManager
 *
 * 2 балла + (2 балла за каждый фильтр + 1 балл за тест на свой фильтр) ИТОГО 11
 */
public interface TextAnalyzer {

    static TextAnalyzer createTooLongAnalyzer(long maxLength) {
        return new TextAnalyzer() {
            @Override
            public FilterType Analyze(String str) {
                if(str.length()>maxLength){
                    return FilterType.TOO_LONG;
                }
                return FilterType.GOOD;
            }
        };
    }

    static TextAnalyzer createSpamAnalyzer(String[] spam) {
        return new TextAnalyzer() {
            @Override
            public FilterType Analyze(String str) {
                for (String spamWord:
                     spam) {
                    if(str.contains(spamWord)){
                        return FilterType.SPAM;
                    }
                }
                return FilterType.GOOD;
            }
        };
    }

    static TextAnalyzer createNegativeTextAnalyzer() {
        return new TextAnalyzer() {
            @Override
            public FilterType Analyze(String str) {
                String[] negWords = {"=(", ":(", ":|"};
                for (String spamWord:
                        negWords) {
                    if(str.contains(spamWord)){
                        return FilterType.NEGATIVE_TEXT;
                    }
                }
                return FilterType.GOOD;
            }
        };
    }

    public FilterType Analyze(String str);
    /**
     * придумать свой фильтр
     */
    static <T> TextAnalyzer createCustomAnalyzer(T something) {
        return null;
    }
}
