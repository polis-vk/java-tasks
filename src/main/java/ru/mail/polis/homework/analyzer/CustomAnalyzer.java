package ru.mail.polis.homework.analyzer;

//Фильтр на проверку, что ТЕКСТ НЕ НАПИСАН КАПСОМ
public class CustomAnalyzer implements TextAnalyzer {
    private final int priority;

    public CustomAnalyzer(int priority) {
        this.priority = priority;
    }

    @Override
    public FilterType getType() {
        return FilterType.CUSTOM;
    }

    @Override
    public final int getPriority() {
        return priority;
    }

    @Override
    public boolean isValid(String text) {
        if (text.length() == 0) {
            return true;
        }
        return !text.equals(text.toUpperCase());
    }
}
