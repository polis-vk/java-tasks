package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private final String[] SMILE = {"=(", ":(", ":|"};

    public boolean analyzer(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return checkWord(text, SMILE);
    }

    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
