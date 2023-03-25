package ru.mail.polis.homework.analyzer;

public class LongTextAnalyze implements TextAnalyzer {


    long maxLenght;

    LongTextAnalyze(long maxLenght) {
        this.maxLenght = maxLenght;
    }

    @Override
    public FilterType analyze(String str) {
        if (str.length() > this.maxLenght) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

}
