package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter implements TextAnalyzer {

    private static final String[] NEGATIVE = new String[]{"=(", ":(", ":|"};

    public NegativeTextFilter() {
    }


    @Override
    public FilterType analyzer(String str) {
        for (String temp : NEGATIVE) {
            if (str.contains(temp)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }

        return FilterType.GOOD;
    }
}
