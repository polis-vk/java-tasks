package ru.mail.polis.homework.analyzer;

//Фильтр на проверку, что ТЕКСТ НЕ НАПИСАН КАПСОМ
public class CustomAnalyzer implements TextAnalyzer {
    @Override
    public FilterType getType() {
        return FilterType.CUSTOM;
    }

    @Override
    public byte getPriority() {
        return 4;
    }

    @Override
    public boolean isValid(String text) {
        if (text == null || text.length() == 0) {
            return true;
        }
        return !text.equals(text.toUpperCase());
    }
}
