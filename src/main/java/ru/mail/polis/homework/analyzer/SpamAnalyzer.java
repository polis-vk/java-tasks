package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spamWords;

    public SpamAnalyzer(String[] spamWords) {
        this.spamWords = spamWords;
    }

    @Override
    public boolean isBadText(String text) {
        boolean resultAnalyze = false;
        for (int i = 0; i < spamWords.length; i++) {
            resultAnalyze = text.contains(spamWords[i]);
            if (resultAnalyze) {
                return resultAnalyze;
            }
        }
        return resultAnalyze;
    }

    @Override
    public FilterType getTypeFilter() {
        return FilterType.SPAM;
    }
}
