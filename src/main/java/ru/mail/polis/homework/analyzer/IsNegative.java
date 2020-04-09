package ru.mail.polis.homework.analyzer;

public class IsNegative implements TextAnalyzer {

    private static final String[] emotions = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String arg) {
        for (String s : emotions) {
            if (arg.contains(s)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterAnswer() {
        return FilterType.NEGATIVE_TEXT;
    }
}
