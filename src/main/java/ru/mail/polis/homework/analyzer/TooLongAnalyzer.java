package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {

    private long maxLength;
    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }
}
