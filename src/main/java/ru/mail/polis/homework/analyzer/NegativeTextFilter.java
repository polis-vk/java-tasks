package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter extends SpamFilter {
    public NegativeTextFilter(String[] spam) {
        super(spam);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
