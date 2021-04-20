package ru.mail.polis.homework.analyzer;

// Фильтр на проверку неправильных символов (для паролей)
public class CustomAnalyzer implements TextAnalyzer {
    private static final String INVALID_SYMBOLS = "! \\ \" № ; % : ? * ( ) + - ! @ # $ % ^ & * [ ] { } , .";
    private final int PRIORITY;

    public CustomAnalyzer(int priority) {
        this.PRIORITY = priority;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean isPassed(String str) {
        return str != null && !TextAnalyzer.contains(str, INVALID_SYMBOLS.split(" "));
    }
}
