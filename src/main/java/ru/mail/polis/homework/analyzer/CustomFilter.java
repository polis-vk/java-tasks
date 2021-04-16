package ru.mail.polis.homework.analyzer;

// Фильтр, проверяющий, не содержит ли комментарий больше определенных символов, чем нужно
public class CustomFilter implements TextAnalyzer {
    private static final FilterType type = FilterType.CUSTOM;
    private static final byte priority = 1;

    private final char symbol;
    private final int count;

    public CustomFilter(char symbol, int count) {
        this.symbol = symbol;
        this.count = count;
    }

    @Override
    public byte getPriority() {
        return priority;
    }

    @Override
    public FilterType getType() {
        return type;
    }

    @Override
    public boolean isNotCorrect(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        byte tempCount = 0;
        for (char ch : text.toCharArray()) {
            if (ch == symbol) {
                tempCount++;
            }
        }
        return tempCount >= count;
    }
}
