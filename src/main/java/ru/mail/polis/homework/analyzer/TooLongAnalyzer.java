package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    public final long maxAdmissibleLength;

    TooLongAnalyzer(long maxAdmissibleLength) {
        this.maxAdmissibleLength = maxAdmissibleLength;
    }

    @Override
    public boolean check(String text) {
        return text.length() > maxAdmissibleLength;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
}
