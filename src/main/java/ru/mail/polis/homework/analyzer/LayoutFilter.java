package ru.mail.polis.homework.analyzer;

public class LayoutFilter implements TextAnalyzer {
    @Override
    public FilterType analyze(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }
        char x;
        for (int i = 0; i < str.length(); i++) {
            x = str.charAt(i);
            if (x >= 'a' && x <= 'z' || x > 'A' && x <= 'Z') {
                return FilterType.CUSTOM;
            }

        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return FilterType.CUSTOM.getType();
    }
}
