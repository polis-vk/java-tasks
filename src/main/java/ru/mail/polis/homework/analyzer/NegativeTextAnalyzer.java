package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] negativeEmotions = new String[]{"=(", ":(", ":|"};

    public FilterType analyze(String text) {
        for (String smile :
                negativeEmotions) {
            if (text.contains(smile)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }
}
