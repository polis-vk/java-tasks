package ru.mail.polis.homework.analyzer;

public class IsNegative extends StringAnalyzer implements TextAnalyzer {

    private static final String[] EMOTIONS = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String arg) {
        return stringAnalyzer(arg, EMOTIONS, FilterType.NEGATIVE_TEXT);
    }

    @Override
    public FilterType getFilterAnswer() {
        return FilterType.NEGATIVE_TEXT;
    }
}
