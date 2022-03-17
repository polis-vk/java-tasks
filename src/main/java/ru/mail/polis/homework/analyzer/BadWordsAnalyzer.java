package ru.mail.polis.homework.analyzer;

public class BadWordsAnalyzer implements TextAnalyzer {

    private String[] badWords = new String[]{"=(", ":(", ":|"};
    private boolean receivedSpam;

    public BadWordsAnalyzer(String[] badWords) {
        receivedSpam = true;
        this.badWords = badWords;
    }

    public BadWordsAnalyzer() {
    }

    @Override
    public FilterType analyze(String text) {
        for (String negativeWord : badWords) {
            if (text.contains(negativeWord)) {
                if (receivedSpam) {
                    return FilterType.SPAM;
                }

                return FilterType.NEGATIVE_TEXT;
            }
        }

        return FilterType.GOOD;
    }

}
