package ru.mail.polis.homework.analyzer;

//Возвращает ENGLISH_TEXT, если все буквы text английские. Допустимые символы - пробелы, запятая и точка
public class EnglishTextAnalyzer implements TextAnalyzer {

    @Override
    public FilterType filterType(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!((text.charAt(i) >= 65 && text.charAt(i) <= 90 || text.charAt(i) >= 97 && text.charAt(i) <= 122)
                    || text.charAt(i) == 44 || text.charAt(i) == 46 || text.charAt(i) == 32)) {
                return FilterType.GOOD;
            }
        }
        return FilterType.ENGLISH_TEXT;
    }

    @Override
    public FilterType type(String text) {
        return FilterType.ENGLISH_TEXT;
    }

    @Override
    public int priorityInfo() {
        return 3;
    }
}
