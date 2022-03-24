package ru.mail.polis.homework.analyzer;

public class CrossedOutLettersAnalyzer implements TextAnalyzer {
    protected final String[] letter = new String[]{"ø", "a̷", "i̷"};

    @Override
    public FilterType analyze(String text) {
        if (text == null) {
            return FilterType.GOOD;
        }
        for (String words : letter) {
            if (text.contains(words)) {
                return FilterType.CROSSED_OUT_LETTERS;
            }
        }
        return FilterType.GOOD;
    }
}
