package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer extends Analyzer implements TextAnalyzer {

    TooLongAnalyzer(long maxLength) {
        this.length = maxLength;
        this.type = FilterType.TOO_LONG;
    }

    @Override
    public boolean FilterWorked(String str) {
        return this.length <= str.length();
    }
}

