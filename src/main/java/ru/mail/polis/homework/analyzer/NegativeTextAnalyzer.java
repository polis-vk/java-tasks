package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    public static final String[] NEG_EMOTION = {"=(", ":(", ":|"};

    public FilterType check(String txt) {
        if (TextAnalyzer.find(NEG_EMOTION, txt)) {
            return FilterType.NEGATIVE_TEXT;
        }
        return null;
    }
}

