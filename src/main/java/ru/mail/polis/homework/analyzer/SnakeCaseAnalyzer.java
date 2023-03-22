package ru.mail.polis.homework.analyzer;

public class SnakeCaseAnalyzer implements TextAnalyzer {
    static final private FilterType filterType = FilterType.SNAKE_CASE;

    @Override
    public boolean filterWorked(String inputString) {
        for (int i = 1; i < inputString.length() - 1; i++) {
            if (inputString.charAt(i) != '_') {
                continue;
            }
            if (inputString.charAt(i - 1) != ' ' && inputString.charAt(i + 1) != ' ') {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }
}
