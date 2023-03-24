package ru.mail.polis.homework.analyzer;

public class TooShortWordAnalyzer implements TextAnalyzer{
    private final int minLengthWord;

    public TooShortWordAnalyzer(int minLengthWord) {
        this.minLengthWord = minLengthWord;
    }

    @Override
    public boolean analyze(String text) {
        String[] wordsArr = text.split("[ ,.\"/;:)=(|!<>]+");
        for (String word : wordsArr) {
            if (word.length() < minLengthWord && word.length() != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_SHORT_WORD;
    }
}
