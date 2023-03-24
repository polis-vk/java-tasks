package ru.mail.polis.homework.analyzer;

public class LayoutFillter implements TextAnalyzer {

    @Override
    public FilterType analyze(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }
        FilterType result = FilterType.GOOD;

        for (char x : str.toCharArray()) {
            if (x >= 'a' && x <= 'z' || x > 'A' && x <= 'Z') {
                result = FilterType.CUSTOM;
                break;
            }

        }
        return result;
    }
}
