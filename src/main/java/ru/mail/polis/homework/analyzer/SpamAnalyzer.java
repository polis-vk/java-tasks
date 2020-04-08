package ru.mail.polis.homework.analyzer;
public class SpamAnalyzer implements TextAnalyzer {
    private final String[] badWords;

    public SpamAnalyzer(String[] targetWords) {
        this.badWords = targetWords;
    }

    public boolean check(String txt) {
        for (String currentSymb : badWords) {
            if (txt.contains(currentSymb)) {
                return true;
            }
        }
        return false;
    }

    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}




