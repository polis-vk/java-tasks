package ru.mail.polis.homework.analyzer;

public class NegativeAnalyzer implements TextAnalyzer {
    final String[] gloomyFaces = {"=(", ":(", ":|"};
    private Integer priority;

    public NegativeAnalyzer() {
        priority = FilterType.NEGATIVE_TEXT.ordinal();
    }

    public FilterType analysis(String text) {
        for (String face : gloomyFaces) {
            if (text.contains(face)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    public Integer getPriority() {
        return priority;
    }
}
